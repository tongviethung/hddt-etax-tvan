package vn.teca.hddt.etax.tvan.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JAXBXPath {
    private String errMessage = "";

    public String getXPathFromErr(String err,Class cls){

        String[] splitErr = err.split(",");
        Stream.of(splitErr).map(String::trim).forEach(s->{
            String[] splitStr = s.split(":");
            if(splitStr.length>1){
                //Get XPath
                XmlRootElement xmlRoot = (XmlRootElement) cls.getDeclaredAnnotation(XmlRootElement.class);
                StringBuilder xPath = new StringBuilder(xmlRoot.name());
                ContainerClass clsTemp = new ContainerClass(cls);
                String[] splitNameProperty = splitStr[0].split("\\.");
                for(int i=0;i<splitNameProperty.length;i++){
                    if(i==splitNameProperty.length-1){
                        xPath.append(getXPathProperties(splitNameProperty[i].trim(), clsTemp));
                    }else{
                        xPath.append(getXPathPropertiesAsObject(splitNameProperty[i].trim(), clsTemp));
                    }
                }

                errMessage+= xPath + " : " + splitStr[1].trim()+"\n";

            }
        });
        return errMessage.substring(0,errMessage.length()-1);
    }
    private String getXPathProperties(String nameProperties,ContainerClass clsTemp){
        String xPath = "";
        try {
            xPath+=getXPathAnnotation(nameProperties,clsTemp,-1);
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        finally{
            return xPath;
        }
    }

    private String getXPathPropertiesAsObject(String nameProperties,ContainerClass clsTemp){
        //use if type properties as Object or List

        String xPath="";

        Pattern pattern = Pattern.compile("(?<nameProperties>.+)(?<stt>\\[\\d+\\])");
        Matcher matcher = pattern.matcher(nameProperties);


        try {
            if(matcher.find()){
                //property is list
                nameProperties = matcher.group("nameProperties");
                xPath+=getXPathAnnotation(nameProperties,clsTemp,1)+matcher.group("stt");
            }else{
                xPath+=getXPathAnnotation(nameProperties,clsTemp,0);
            }


        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        finally {
            return xPath;
        }
    }

    private String getXPathAnnotation(String nameProperties,ContainerClass clsTemp,int flagListProperty) throws Exception{
        String xPath = "";
        Field fd = clsTemp.getCls().getDeclaredField(nameProperties);
        XmlPath xmlPath = fd.getDeclaredAnnotation(XmlPath.class);
        if(xmlPath != null){
            if(xmlPath.value().contains("/text()")) {
                xPath += "/" + xmlPath.value().substring(0, xmlPath.value().length() - 7);
            }else{
                xPath+= "/"+xmlPath.value();
            }
        }
        else{
            XmlElement xmlElement = fd.getDeclaredAnnotation(XmlElement.class);
            if(xmlElement != null){
                xPath+="/"+xmlElement.name();
            }
        }
        if(flagListProperty==1){
            clsTemp.setCls((Class)((ParameterizedType)fd.getGenericType()).getActualTypeArguments()[0]);
        }
        else if(flagListProperty==0){
            clsTemp.setCls(fd.getType());
        }
        return xPath;
    }

    @Data
    @AllArgsConstructor
    private class ContainerClass{
        private Class cls;
    }
}
