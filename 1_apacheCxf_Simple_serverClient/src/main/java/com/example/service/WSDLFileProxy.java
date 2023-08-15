package com.example.service;

public class WSDLFileProxy implements com.example.service.WSDLFile_PortType {
  private String _endpoint = null;
  private com.example.service.WSDLFile_PortType wSDLFile_PortType = null;
  
  public WSDLFileProxy() {
    _initWSDLFileProxy();
  }
  
  public WSDLFileProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSDLFileProxy();
  }
  
  private void _initWSDLFileProxy() {
    try {
      wSDLFile_PortType = (new com.example.service.WSDLFile_ServiceLocator()).getWSDLFileSOAP();
      if (wSDLFile_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSDLFile_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSDLFile_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSDLFile_PortType != null)
      ((javax.xml.rpc.Stub)wSDLFile_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.example.service.WSDLFile_PortType getWSDLFile_PortType() {
    if (wSDLFile_PortType == null)
      _initWSDLFileProxy();
    return wSDLFile_PortType;
  }
  
  public java.lang.String newOperation(java.lang.String in) throws java.rmi.RemoteException{
    if (wSDLFile_PortType == null)
      _initWSDLFileProxy();
    return wSDLFile_PortType.newOperation(in);
  }
  
  
}