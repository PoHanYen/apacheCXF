/**
 * WSDLFile_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.service;

public interface WSDLFile_Service extends javax.xml.rpc.Service {
    public java.lang.String getWSDLFileSOAPAddress();

    public com.example.service.WSDLFile_PortType getWSDLFileSOAP() throws javax.xml.rpc.ServiceException;

    public com.example.service.WSDLFile_PortType getWSDLFileSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
