package org.pfccap.education.entities;

/**
 * Created by USUARIO on 05/07/2017.
 */
import java.util.List;

public class Validation {

        private int responseCode;
        private List<Object> response = null;

        public Integer getResponseCode() {
                return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
                this.responseCode = responseCode;
        }

        public List<Object> getResponse() {
                return response;
        }

        public void setResponse(List<Object> response) {
                this.response = response;
        }

}
