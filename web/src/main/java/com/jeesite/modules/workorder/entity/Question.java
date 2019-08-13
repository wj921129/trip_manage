package com.jeesite.modules.workorder.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

@Table(name="trip_work_order_question", alias="a", columns={
        @Column(includeEntity=TreeEntity.class),
        @Column(includeEntity=DataEntity.class),
        @Column(name="question_code", attrName="questionCode", label="问题编码", isPK=true),
        @Column(name="question_name", attrName="questionName", label="问题名称", queryType=QueryType.LIKE, isTreeName=true),
        @Column(name="kid", attrName="kid", label="kid"),
}, orderBy="a.tree_sorts, a.question_code"
)
public class Question  extends TreeEntity<Question> {

    private static final long serialVersionUID = 1L;
    private String kid;
    private String questionCode;		// 节点编码
    private String questionName;		// 节点名称

    public Question() {
        this(null);
    }

    public Question(String id){
        super(id);
    }

    @Override
    public Question getParent() {
        return parent;
    }

    @Override
    public void setParent(Question parent) {
        this.parent = parent;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    @NotBlank(message="问题名称不能为空")
    @Length(min=0, max=200, message="问题名称长度不能超过 200 个字符")
    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
