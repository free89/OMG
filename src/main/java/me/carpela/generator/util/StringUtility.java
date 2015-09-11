/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.carpela.generator.util;

import java.util.StringTokenizer;

/**
 * 
 * @author Jeff Butler
 */
public class StringUtility {

    /**
     * Utility class. No instances allowed
     */
    private StringUtility() {
        super();
    }

    public static boolean stringHasValue(String s) {
        return s != null && s.length() > 0;
    }

    public static String composeFullyQualifiedTableName(String catalog,
            String schema, String tableName, char separator) {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append(separator);
        } else {
            if (sb.length() > 0) {
                sb.append(separator);
            }
        }

        sb.append(tableName);

        return sb.toString();
    }

    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static String escapeStringForXml(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("&quot;"); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s); //$NON-NLS-1$
    }

    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }
    
    /**
     * ���շ�ʽ�������ַ���ת��Ϊ�»��ߴ�д��ʽ�����ת��ǰ���շ�ʽ�������ַ���Ϊ�գ��򷵻ؿ��ַ�����</br>
     * ���磺HelloWorld->HELLO_WORLD
     * @param name ת��ǰ���շ�ʽ�������ַ���
     * @return ת�����»��ߴ�д��ʽ�������ַ���
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // ����һ���ַ�����ɴ�д
            result.append(name.substring(0, 1).toUpperCase());
            // ѭ�����������ַ�
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // �ڴ�д��ĸǰ����»���
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // �����ַ�ֱ��ת�ɴ�д
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }
     
    /**
     * ���»��ߴ�д��ʽ�������ַ���ת��Ϊ�շ�ʽ�����ת��ǰ���»��ߴ�д��ʽ�������ַ���Ϊ�գ��򷵻ؿ��ַ�����</br>
     * ���磺HELLO_WORLD->HelloWorld
     * @param name ת��ǰ���»��ߴ�д��ʽ�������ַ���
     * @return ת������շ�ʽ�������ַ���
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // ���ټ��
        if (name == null || name.isEmpty()) {
            // û��Ҫת��
            return "";
        } else if (!name.contains("_")) {
            // �����»��ߣ���������ĸСд
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // ���»��߽�ԭʼ�ַ����ָ�
        String camels[] = name.split("_");
        for (String camel :  camels) {
            // ����ԭʼ�ַ����п�ͷ����β���»��߻�˫���»���
            if (camel.isEmpty()) {
                continue;
            }
            // �����������շ�Ƭ��
            if (result.length() == 0) {
                // ��һ���շ�Ƭ�Σ�ȫ����ĸ��Сд
                result.append(camel.toLowerCase());
            } else {
                // �������շ�Ƭ�Σ�����ĸ��д
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
