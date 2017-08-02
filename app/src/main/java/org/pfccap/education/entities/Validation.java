package org.pfccap.education.entities;

/**
 * Created by USUARIO on 05/07/2017.
 */

public class Validation {

        private int responseCode;
        private boolean cervix;
        private boolean breast;

        public int getResponseCode() {
                return responseCode;
        }

        public void setResponseCode(int responseCode) {
                this.responseCode = responseCode;
        }

        public boolean isCervix() {
                return cervix;
        }

        public void setCervix(boolean cervix) {
                this.cervix = cervix;
        }

        public boolean isBreast() {
                return breast;
        }

        public void setBreast(boolean breast) {
                this.breast = breast;
        }
}
