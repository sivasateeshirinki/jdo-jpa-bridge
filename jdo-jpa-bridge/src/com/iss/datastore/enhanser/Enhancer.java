package com.iss.datastore.enhanser;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
/**
 * This Class enhances the entities by adding persistence annotations and removing the 
 * unnecessary code. 
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class Enhancer {
	/**
	 * 
	 * @param className name of the entity class to be enhanced. 
	 * @param basePackage base package of entity class.
	 * @param classPath class path to to write the enhanced class file.
	 */
	public void enhance(String className,String basePackage,String classPath) {
		ClassPool pool = ClassPool.getDefault();
		boolean flag=false;
		String getter="";
		String setter="";
		try {
			pool.insertClassPath(new ClassClassPath(this.getClass()));
			CtClass oldClass = pool.get(basePackage+"."+className);
			CtClass newClass = pool.makeClass(basePackage+"."+className);
			CtField fieldList[] = oldClass.getDeclaredFields();
			for (CtField field : fieldList) {          
				Object annotArray[] = field.getAnnotations();
				if (annotArray.length > 0) {
					CtField newField = new CtField(field, newClass);
					newClass.addField(newField);
					String fieldName = field.getName();
					setter = "set"
							+ fieldName.substring(0, 1).toUpperCase();
					if(field.getType().getSimpleName().equals("boolean"))
					{
					getter = "is"
							+ fieldName.substring(0, 1).toUpperCase();
					}
					else
					{
						getter = "get"
								+ fieldName.substring(0, 1).toUpperCase();
					}
					if (fieldName.length() > 1) {
						String tail = fieldName.substring(1);
						setter = setter + tail;
						getter = getter + tail;
					}
					CtMethod oldGetter = oldClass.getDeclaredMethod(getter);
					CtMethod oldSetter = oldClass.getDeclaredMethod(setter);
					CtMethod newGetter = new CtMethod(oldGetter, newClass, null);
					CtMethod newSetter = new CtMethod(oldSetter, newClass, null);
					newGetter.setBody("{return " + fieldName + ";}");
					newSetter.setBody("{" + fieldName + "=$1;}");
					newClass.addMethod(newSetter);
					newClass.addMethod(newGetter);
					flag=true;
				}
			}
				if(flag)
				{
			        ClassFile oldClassFile=oldClass.getClassFile();
			        AnnotationsAttribute annotationAttribute=(AnnotationsAttribute)oldClassFile.getAttribute(AnnotationsAttribute.visibleTag);
			        if(annotationAttribute!=null)
			        {
			        	ClassFile newClassFile=newClass.getClassFile();
				        ConstPool constpool = newClassFile.getConstPool();
				        Annotation entityAnnot = new Annotation("javax.persistence.Entity", constpool);
			        	annotationAttribute.addAnnotation(entityAnnot);
			        	newClassFile.addAttribute(annotationAttribute);
			        }	
			        newClass.writeFile(classPath);
				}
	
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (CannotCompileException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
