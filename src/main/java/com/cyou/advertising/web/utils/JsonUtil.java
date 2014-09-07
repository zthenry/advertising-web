package com.cyou.advertising.web.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final Map<Class<?>, List<Field>> fieldCache;

	public static Object json2object(String jsonStr, Class<?> clazz) {
		if ((jsonStr == null) || (jsonStr.length() == 0)) {
			return null;
		}

		Object object = null;
		try {
			if ((jsonStr.startsWith("[")) && (jsonStr.endsWith("]")))
				object = OBJECT_MAPPER.readValue(jsonStr, TypeFactory
						.collectionType(List.class, clazz));
			else
				object = OBJECT_MAPPER.readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return object;
	}

	public static Map<String, Object> json2map(String jsonStr) {
		Map map = null;
		try {
			map = (Map) OBJECT_MAPPER.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return map;
	}

	public static Map<String, Object> json2map(String jsonStr, Class<?> clazz) {
		return json2map(jsonStr, "data", clazz);
	}

	public static Map<String, Object> json2map(String jsonStr,
			String fieldname, Class<?> clazz) {
		Map map = null;
		try {
			map = (Map) OBJECT_MAPPER.readValue(jsonStr, Map.class);
			Object value = map.get(fieldname);
			Class valueClazz = value.getClass();

			if (valueClazz == LinkedHashMap.class) {
				Object clazzObj = OBJECT_MAPPER.readValue(getJsonData(jsonStr,
						fieldname), clazz);
				map.put(fieldname, clazzObj);
			} else if (valueClazz == ArrayList.class) {
				Object clazzObj = OBJECT_MAPPER.readValue(getJsonData(jsonStr,
						fieldname), TypeFactory.collectionType(List.class,
						clazz));

				map.put(fieldname, clazzObj);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return map;
	}

	public static Object getObject(Class<?> clazz,
			LinkedHashMap<String, Object> map) {
		Object clazzObj = null;
		try {
			clazzObj = clazz.newInstance();
			List<Field> fields = getFields(clazz);
			for (Field field : fields) {
				Object value = map.get(field.getName());
				if (value == null) {
					continue;
				}
				set(clazzObj, field, value);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return clazzObj;
	}

	private static void set(Object targetObj, Field field, Object value) {
		try {
			Class fieldClazz = field.getType();
			Type fieldType = field.getGenericType();
			Class valueClazz = value.getClass();

			if (((value instanceof String)) || ((value instanceof Integer))
					|| ((value instanceof Long)) || ((value instanceof Float))
					|| ((value instanceof Double))
					|| ((value instanceof Boolean))) {
				field.set(targetObj, value);
			} else if (valueClazz == LinkedHashMap.class) {
				List<Field> subFields = getFields(fieldClazz);
				if (subFields == null) {
					return;
				}
				Object subObject = fieldClazz.newInstance();
				LinkedHashMap map = (LinkedHashMap) value;
				for (Field subField : subFields) {
					Object subValue = map.get(subField.getName());
					if (subValue == null) {
						continue;
					}
					set(subObject, subField, subValue);
				}
				field.set(targetObj, subObject);
			} else if (valueClazz == ArrayList.class) {
				if ((fieldType instanceof Class)) {
					if (fieldClazz.isArray()) {
						Class paramClazz = fieldClazz.getComponentType();
						ArrayList tmpList = (ArrayList) value;
						Object objArr = Array.newInstance(paramClazz, tmpList
								.size());
						for (int i = 0; i < tmpList.size(); i++) {
							LinkedHashMap tmpMap = (LinkedHashMap) tmpList
									.get(i);
							Object obj = getObject(paramClazz, tmpMap);
							Array.set(objArr, i, obj);
						}
						field.set(targetObj, objArr);
					} else {
						field.set(targetObj, value);
					}
				} else if ((fieldType instanceof ParameterizedType)) {
					if (fieldClazz == List.class) {
						Class paramClazz = (Class) ((ParameterizedType) field
								.getGenericType()).getActualTypeArguments()[0];

						if ((paramClazz != String.class)
								&& (paramClazz != Integer.class)) {
							ArrayList tmpList = (ArrayList) value;
							List list = new ArrayList();
							for (Iterator i$ = tmpList.iterator(); i$.hasNext();) {
								Object obj = i$.next();
								LinkedHashMap tmpMap = (LinkedHashMap) obj;
								Object clazzObj = getObject(paramClazz, tmpMap);
								if (clazzObj != null) {
									list.add(clazzObj);
								}
							}
							field.set(targetObj, list);
						} else {
							field.set(targetObj, value);
						}
					} else {
						field.set(targetObj, value);
					}
				} else if ((fieldType instanceof GenericArrayType)) {
					if (fieldClazz == String.class)
						field.set(targetObj, ((ArrayList) value)
								.toArray(new String[0]));
					else if (fieldClazz == Integer.class)
						field.set(targetObj, ((ArrayList) value)
								.toArray(new Integer[0]));
					else {
						field.set(targetObj, value);
					}
				} else
					field.set(targetObj, value);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	private static List<Field> getFields(Class<?> clazz) {
		List fields = (List) fieldCache.get(clazz);
		if (fields == null) {
			synchronized (fieldCache) {
				fieldCache.put(clazz, fields = collectFieldInfo(clazz));
			}
		}
		return fields;
	}

	private static List<Field> collectFieldInfo(Class<?> clazz) {
		List list = new ArrayList();
		AccessibleObject[] fields = clazz.getDeclaredFields();
		for (AccessibleObject ao : fields) {
			ao.setAccessible(true);
			Field field = (Field) ao;
			list.add(field);
		}
		return list;
	}

	public static String toJson(Object obj) {
		String jsonstr = null;
		try {
			jsonstr = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return jsonstr;
	}

	public static Object toObject(String json, Class<?> objclass) {
		if ((json == null) || (json.length() == 0)) {
			return null;
		}

		Object object = null;
		try {
			object = OBJECT_MAPPER.readValue(json, objclass);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return object;
	}

	public static Map<String, String> getRootJson(String json) {
		JsonNode rootNode = null;
		try {
			rootNode = OBJECT_MAPPER.readTree(json);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		if (rootNode == null) {
			return null;
		}
		Map map = new HashMap();
		Iterator names = rootNode.getFieldNames();

		while (names.hasNext()) {
			String name = (String) names.next();
			JsonNode node = rootNode.path(name);
			if (node.isNull()) {
				continue;
			}
			String value = null;
			if ((node.isObject()) || (node.isArray()) || (node.isPojo()))
				value = node.toString();
			else {
				value = node.getValueAsText();
			}
			map.put(name, value);
		}
		return map;
	}

	public static String getJsonData(String json, String fieldname) {
		JsonNode rootNode = null;
		try {
			rootNode = OBJECT_MAPPER.readTree(json);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		if (rootNode == null) {
			return null;
		}
		String value = null;
		Iterator names = rootNode.getFieldNames();
		while (names.hasNext()) {
			String name = (String) names.next();
			if (!fieldname.equals(name)) {
				continue;
			}
			JsonNode node = rootNode.path(name);
			if ((node.isObject()) || (node.isArray()) || (node.isPojo()))
				value = node.toString();
			else {
				value = node.getValueAsText();
			}
		}

		return value;
	}

	public static String getRawJsonData(String json, String fieldname) {
		JsonNode rootNode = null;
		try {
			rootNode = OBJECT_MAPPER.readTree(json);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		if (rootNode == null) {
			return null;
		}
		String value = null;
		Iterator names = rootNode.getFieldNames();
		while (names.hasNext()) {
			String name = (String) names.next();
			if (!fieldname.equals(name)) {
				continue;
			}
			JsonNode node = rootNode.path(name);
			value = node.toString();
		}

		return value;
	}

	static {
		OBJECT_MAPPER.getSerializationConfig().setSerializationInclusion(
				JsonSerialize.Inclusion.NON_NULL);
		OBJECT_MAPPER.getDeserializationConfig()
				.set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);

		fieldCache = new HashMap();
	}
}
