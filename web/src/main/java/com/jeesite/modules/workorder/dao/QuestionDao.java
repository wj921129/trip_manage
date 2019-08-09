/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.workorder.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.test.entity.TestTree;
import com.jeesite.modules.workorder.entity.Question;

@MyBatisDao
public interface QuestionDao extends TreeDao<Question> {
	
}