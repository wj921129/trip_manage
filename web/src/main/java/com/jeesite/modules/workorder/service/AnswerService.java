package com.jeesite.modules.workorder.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.workorder.dao.AnswerDao;
import com.jeesite.modules.workorder.entity.Answer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class AnswerService extends CrudService<AnswerDao, Answer> {

    @Override
    public Answer get(Answer answer) {
        return super.get(answer);
    }

    @Override
    public List<Answer> findList(Answer answer){
        return super.findList(answer);
    }

    @Override
    public Page<Answer> findPage(Answer answer){
        return super.findPage(answer);
    }

    public void save(Answer answer){
        super.save(answer);
    }

    public void update(Answer answer){
        super.update(answer);
    }

    public void delete(Answer answer){
        super.delete(answer);
    }

}
