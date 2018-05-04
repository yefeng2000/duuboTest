package com.example.demo.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.codehaus.xfire.util.dom.DOMOutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.example.demo.dto.IDCardValidErrorDto;
import com.example.demo.service.IDCardValidService;
import com.example.demo.util.JaxbUtil.CollectionWrapper;

@Component
public class IDCardValidServiceTest {

	public static final String URL = "https://ws.nciic.org.cn/nciic_ws/services/";
	@Value("${license_path}")
	private String licensePath;
	public static void main(String[] args) throws Exception {
		String license = "E:\\soft\\idcardvalidlicense.txt";
		String con = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><ROWS><INFO><SBM>深圳市汇合发展有限公司</SBM></INFO><ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW><ROW FSD=\"110105\" YWLX=\"测试\" ><GMSFHM>412829198809243631</GMSFHM><XM>王健</XM></ROW></ROWS>";
		String res = new IDCardValidServiceTest().executeClient("NciicServices", license, con);
		
		JaxbUtil jaxbUtil = new JaxbUtil(IDCardValidErrorDto.class,CollectionWrapper.class);
		IDCardValidErrorDto resp = jaxbUtil.fromXml(res);
		System.out.println("resp:"+(resp.rows.get(0).errorMsg));
	}
	
	public void test() throws Exception {
		String license = "E:\\soft\\idcardvalidlicense.txt";
		String con = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><ROWS><INFO><SBM>深圳市汇合发展有限公司</SBM></INFO><ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW><ROW FSD=\"110105\" YWLX=\"测试\" ><GMSFHM>412829198809243631</GMSFHM><XM>王健</XM></ROW></ROWS>";
		
		/*BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(ResourceUtils.getFile(licensePath)));
			license = in.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(in != null)
				in.close();
		}*/
		
		
		String res = new IDCardValidServiceTest().executeClient("NciicServices", licensePath, con);
		
		JaxbUtil jaxbUtil = new JaxbUtil(IDCardValidErrorDto.class,CollectionWrapper.class);
		IDCardValidErrorDto resp = jaxbUtil.fromXml(res);
		System.out.println("resp:"+(resp.rows.get(0).errorMsg));
	}
	
	public String executeClient(String serviceName,String license,String condition) throws Exception {
		//调用生产环境接口时，需要采用https
		ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
		Protocol protocol = new Protocol("https", easy, 443);
		Protocol.registerProtocol("https", protocol);
		//--核心服务NciicServices
		Service serviceModel = new ObjectServiceFactory().create(IDCardValidService.class, "NciicServices", null, null);
		IDCardValidService service = (IDCardValidService) new XFireProxyFactory().create(serviceModel, URL + serviceName);
		Client client = ((XFireProxy) Proxy.getInvocationHandler(service)).getClient();
		client.addOutHandler(new DOMOutHandler());
		client.setProperty(CommonsHttpMessageSender.GZIP_ENABLED, Boolean.TRUE);
		// 忽略超时
		client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "0");
		String result = null;
		try {
			
			/**
			 * 调用方法
			 */
			result = service.nciicCheck(license, condition);
			System.out.println("==================");
			System.out.println( result);
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
