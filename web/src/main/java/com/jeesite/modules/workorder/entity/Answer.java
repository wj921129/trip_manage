package com.jeesite.modules.workorder.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Table(name="trip_work_order_answer", alias="a", columns={
        @Column(includeEntity= DataEntity.class),
        @Column(name="answer_code", attrName="answerCode", label="答案标识", isPK=true),
        @Column(name="question_kid", attrName="questionKid", label="问题kid"),
        @Column(name="answer", attrName="answer", label="答案"),
}, orderBy="a.create_date DESC"
)
public class Answer extends DataEntity<Answer> {

    private static final long serialVersionUID = 1L;
    private String answerCode;
    private String questionKid;
    private String answer;
    private String questionName;

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getQuestionKid() {
        return questionKid;
    }

    public void setQuestionKid(String questionKid) {
        this.questionKid = questionKid;
    }

    @NotBlank(message="答案不能为空")
    @Length(min=1, max=500, message="答案长度不能超过 500 个字符")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
