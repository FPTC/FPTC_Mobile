package org.pfccap.education.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 11/05/2017.
 */

public class QuestionByCancer {

        private String answers;
        private int id;
        private String info;
        private int order;
        private String text;
        private String typeQuestion;
        private boolean enable;

        public String getAnswers() {
                return answers;
        }

        public void setAnswers(String answers) {
                this.answers = answers;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getInfo() {
                return info;
        }

        public void setInfo(String info) {
                this.info = info;
        }

        public int getOrder() {
                return order;
        }

        public void setOrder(int order) {
                this.order = order;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public String getTypeQuestion() {
                return typeQuestion;
        }

        public void setTypeQuestion(String typeQuestion) {
                this.typeQuestion = typeQuestion;
        }

        public boolean isEnable() {
                return enable;
        }

        public void setEnable(boolean enable) {
                this.enable = enable;
        }
}
