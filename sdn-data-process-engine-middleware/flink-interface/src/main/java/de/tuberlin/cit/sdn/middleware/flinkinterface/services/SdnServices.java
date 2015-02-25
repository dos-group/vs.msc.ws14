package de.tuberlin.cit.sdn.middleware.flinkinterface.services;

public interface SdnServices {
    public String getExecutionHost();
    public void markInstanceAsUnused(String host);
}
