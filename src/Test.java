import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
public class Test {
	public static void main(String[] args) throws Exception{
		String targetEendPoint =  "http://localhost:8088/zwgk/services/FlService" ;   
    	Service service =  new  Service();   
    	Call call = (Call) service.createCall();   
    	call.setTargetEndpointAddress( new  URL(targetEendPoint));   
    	  //方法
    	call.setOperationName( new  QName(targetEendPoint,  "searchFl" ));    
    	//参数
    	String result = (String) call.invoke( new  Object[]{ "hfhb","hfhb123","00010002" });   
    	System.out.println("result:"+result);   

	}
}
