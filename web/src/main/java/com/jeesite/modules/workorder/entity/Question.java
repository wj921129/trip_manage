package com.jeesite.modules.workorder.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import lombok.Data;

@Data
/*@Table(name = "trip_work_order_question", alias = "a", columns = {
        @Column(includeEntity= DataEntity.class),
        @Column(includeEntity=TreeEntity.class),
        @Column(name="kid", attrName="kid", label="问题代码", isPK=true),
        @Column(name="question_name", attrName="questionName", label="问题名称", queryType= QueryType.LIKE, isTreeName=true),
        @Column(name="status", attrName="status", label="状态"),
    }, orderBy="a.tree_sorts, a.area_code")*/
public class Question  extends TreeEntity<Question> {

    private String kid;

    private String parentKid;

    private String questionName;

    private Integer questionOrder;

    private Boolean isLastLevel = true;

    @Override
    public void setParent(Question parent) {
        this.parent = parent;
    }

    @Override
    public Question getParent() {
        return parent;
    }
}
