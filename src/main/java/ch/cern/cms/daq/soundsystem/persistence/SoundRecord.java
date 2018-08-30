package ch.cern.cms.daq.soundsystem.persistence;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SoundRecord {

    @Id
    @GeneratedValue
    Long id;

    String requestType;

    String requestBody;

    @Temporal(TemporalType.TIMESTAMP)
    Date called;

    @Temporal(TemporalType.TIMESTAMP)
    Date executionStarted;

    @Temporal(TemporalType.TIMESTAMP)
    Date executionFinished;

    String response;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Date getCalled() {
        return called;
    }

    public void setCalled(Date called) {
        this.called = called;
    }

    public Date getExecutionStarted() {
        return executionStarted;
    }

    public void setExecutionStarted(Date executionStarted) {
        this.executionStarted = executionStarted;
    }

    public Date getExecutionFinished() {
        return executionFinished;
    }

    public void setExecutionFinished(Date executionFinished) {
        this.executionFinished = executionFinished;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SoundRecord{" +
                "id=" + id +
                ", requestType='" + requestType + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", called=" + called +
                ", executionStarted=" + executionStarted +
                ", executionFinished=" + executionFinished +
                ", response='" + response + '\'' +
                '}';
    }
}
