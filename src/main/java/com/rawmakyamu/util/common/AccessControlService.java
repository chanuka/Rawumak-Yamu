/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.common;

/**
 *
 * @author chanuka
 */
public interface AccessControlService {
    public boolean checkAccess(String method, String userRole);
}
