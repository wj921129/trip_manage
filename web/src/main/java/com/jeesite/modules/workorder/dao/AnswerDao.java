/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.workorder.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.workorder.entity.Answer;

@MyBatisDao
public interface AnswerDao extends CrudDao<Answer> {
	
}