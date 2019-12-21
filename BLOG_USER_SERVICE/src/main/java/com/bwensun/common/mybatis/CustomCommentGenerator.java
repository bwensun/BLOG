package com.bwensun.common.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 增加 swagger 字段注释支持
 *
 * @author daiwenzh5
 * @date 2019/9/8 18:56
 */
public class CustomCommentGenerator extends DefaultCommentGenerator {

    private Properties properties;

    public CustomCommentGenerator() {
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // 获取自定义的 properties
        this.properties.putAll(properties);
    }

    /**
     * 添加 swagger 字段注解
     *
     * @param field              字段
     * @param introspectedTable  表
     * @param introspectedColumn 列
     * @param imports            导入类
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty"));
        String remarks = introspectedColumn.getRemarks();
        field.addAnnotation(this.getSwaggerAnnotation(field.getName(), remarks));
        super.addFieldAnnotation(field, introspectedTable, introspectedColumn, imports);
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
        super.addFieldAnnotation(field, introspectedTable, set);
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 获取列注释
        String remarks = introspectedColumn.getRemarks();
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + remarks);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
/*        if (suppressAllComments) {
            return;
        }*/
        String methodDoc = getMethodDoc(method, introspectedTable);
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + methodDoc);
        method.addJavaDocLine(" *");
        setMethodParam(method);
        setMethodReturn(method, introspectedTable);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
        super.addClassAnnotation(innerClass, introspectedTable, set);
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("dateFormat", "yyyy-MM-dd");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        // 获取表注释
        String remarks = introspectedTable.getRemarks();

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 获取 swagger 注解信息
     *
     * @param name    字段名
     * @param remarks 注释信息
     * @return 返回 swagger 注释字符串
     */
    private String getSwaggerAnnotation(String name, String remarks) {
        StringBuffer buffer = new StringBuffer("@ApiModelProperty(");
        buffer.append("name = \"");
        buffer.append(name);
        if (StringUtility.stringHasValue(remarks)) {
            buffer.append("\", value = \"");
            buffer.append(remarks);
        }
        buffer.append("\")");
        return buffer.toString();
    }

    private String getMethodDoc(Method method, IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        String methodDoc;
        switch (method.getName()){
            case "deleteByPrimaryKey":
                methodDoc = ("根据主键删除" + remarks + "记录");
                break;
            case "selectByPrimaryKey":
                methodDoc = ("根据主键查询" + remarks + "记录");
                break;
            case "insert":
                methodDoc = ("新增记录至" + remarks);
                break;
            case "selectAll":
                methodDoc = ("查询" + remarks + "所有记录");
                break;
            case "updateByPrimaryKey":
                methodDoc = ("根据主键更新" + remarks + "记录");
                break;
            default:
                methodDoc = "description";
                break;
        }
        return methodDoc;
    }

    /**
     * 设置参数注释
     *
     * @param method 方法
     */
    private void setMethodParam(Method method) {
        List<Parameter> parameters = method.getParameters();
        if (parameters.size() != 0){
            parameters.forEach(x ->
                method.addJavaDocLine(" * @param " + x.getName() + ("ID".equalsIgnoreCase(x.getName())?" 主键":" 记录") )
            );
        }
    }

    /**
     * 设置返回值注释
     *
     * @param method 方法
     * @param introspectedTable 表对象
     */
    private void setMethodReturn(Method method, IntrospectedTable introspectedTable) {
        String methodReturn;
        String shortName = method.getReturnType().getShortName();
        if ("int".equalsIgnoreCase(shortName)){
            methodReturn = "受影响的记录数";
        }else if (shortName.startsWith("java.util.List")){
            methodReturn = introspectedTable.getRemarks() + "list";
        }else {
            methodReturn = introspectedTable.getRemarks() + "记录";
        }
        method.addJavaDocLine(" * @return " + methodReturn);
    }

}
