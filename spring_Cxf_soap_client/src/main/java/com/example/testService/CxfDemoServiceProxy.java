package com.example.testService;

public class CxfDemoServiceProxy implements com.example.testService.CxfDemoService {
  private String _endpoint = null;
  private com.example.testService.CxfDemoService cxfDemoService = null;
  
  public CxfDemoServiceProxy() {
    _initCxfDemoServiceProxy();
  }
  
  public CxfDemoServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCxfDemoServiceProxy();
  }
  
  private void _initCxfDemoServiceProxy() {
    try {
      cxfDemoService = (new com.example.service.CxfDemoServiceImplServiceLocator()).getCxfDemoServiceImplPort();
      if (cxfDemoService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cxfDemoService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cxfDemoService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cxfDemoService != null)
      ((javax.xml.rpc.Stub)cxfDemoService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.example.testService.CxfDemoService getCxfDemoService() {
    if (cxfDemoService == null)
      _initCxfDemoServiceProxy();
    return cxfDemoService;
  }
  
  public com.example.testService.Person insert(com.example.testService.Person person) throws java.rmi.RemoteException{
    if (cxfDemoService == null)
      _initCxfDemoServiceProxy();
    return cxfDemoService.insert(person);
  }
  
  public com.example.testService.Person update(com.example.testService.Person person) throws java.rmi.RemoteException{
    if (cxfDemoService == null)
      _initCxfDemoServiceProxy();
    return cxfDemoService.update(person);
  }
  
  
}