package org.mybatis.generator.codegen.mybatis3.controler;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

public class ControllerGenerator extends AbstractJavaGenerator {
	public ControllerGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		progressCallback.startTask(getString("Progress.6", table.toString())); //$NON-NLS-1$
		CommentGenerator commentGenerator = context.getCommentGenerator();
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3ControllerPackage());
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * " + type);
		topLevelClass.addJavaDocLine(" * @auther ");
		topLevelClass.addJavaDocLine(" */");
		topLevelClass.addAnnotation("@RestController");
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(new FullyQualifiedJavaType(""));
		FullyQualifiedJavaType javaService = new FullyQualifiedJavaType(introspectedTable.getMyBatis3ServicePackage());
		FullyQualifiedJavaType tableName = javaService.getShortName(javaService.getShortName());
		String shortNameMapper = toLowerCaseFirstOne(tableName.getShortName());
		field.setName(tableName + " " + shortNameMapper); // $NON-NLS-1$
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		String methodName = introspectedTable.getFindStatementId(1);
		method.setName(methodName); // $NON-NLS-1$
		FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// recordType.getShortName(recordType.getShortName())
		FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
		// String param = toLowerCaseFirstOne(recordType.getShortName());
		method.addParameter(new Parameter(exampleType, "example")); // $NON-NLS-1$
		method.addBodyLine(shortNameMapper + "." + methodName + "(example);"); //$NON-NLS-1$
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		methodName = introspectedTable.getPutStatementId(1);
		method.setName(methodName); // $NON-NLS-1$
		method.addParameter(new Parameter(recordType, "record")); // $NON-NLS-1$
		method.addParameter(new Parameter(exampleType, "example")); // $NON-NLS-1$
		method.addBodyLine(shortNameMapper + "." + methodName + "(record, example);"); //$NON-NLS-1$
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		methodName = introspectedTable.getPostStatementId(1);
		method.setName(methodName); // $NON-NLS-1$
		method.addParameter(new Parameter(recordType, "record")); // $NON-NLS-1$
		method.addBodyLine(shortNameMapper + "." + methodName + "(record);"); //$NON-NLS-1$
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		methodName = introspectedTable.getDelStatementId(1);
		method.setName(methodName); // $NON-NLS-1$
		method.addParameter(new Parameter(exampleType, "example")); // $NON-NLS-1$
		method.addBodyLine(shortNameMapper + "." + methodName + "(example);"); //$NON-NLS-1$
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		topLevelClass.addImportedType(javaService);
		topLevelClass.addImportedType(recordType);
		topLevelClass.addImportedType(exampleType);
		topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
		topLevelClass.addImportedType("org.springframework.web.bind.annotation.RestController");
		// now generate
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (context.getPlugins().modelExampleClassGenerated(topLevelClass, introspectedTable)) {
			answer.add(topLevelClass);
		}
		return answer;
	}

	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
