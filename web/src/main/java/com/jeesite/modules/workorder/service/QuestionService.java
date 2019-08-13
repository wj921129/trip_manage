package com.jeesite.modules.workorder.service;

import com.jeesite.common.service.TreeService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.workorder.dao.QuestionDao;
import com.jeesite.modules.workorder.entity.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class QuestionService  extends TreeService<QuestionDao, Question> {

    /**
     * 获取单条数据
     * @param question
     * @return
     */
    @Override
    public Question get(Question question) {
        return super.get(question);
    }

    /**
     * 查询列表数据
     * @param question
     * @return
     */
    @Override
    public List<Question> findList(Question question) {
        return super.findList(question);
    }

    /**
     * 保存数据（插入或更新）
     * @param question
     */
    @Override
    @Transactional(readOnly=false)
    public void save(Question question) {
        super.save(question);
        // 保存上传图片
        FileUploadUtils.saveFileUpload(question.getId(), "testTree_image");
        // 保存上传附件
        FileUploadUtils.saveFileUpload(question.getId(), "testTree_file");
    }

    /**
     * 更新状态
     * @param question
     */
    @Override
    @Transactional(readOnly=false)
    public void updateStatus(Question question) {
        super.updateStatus(question);
    }

    /**
     * 删除数据
     * @param question
     */
    @Override
    @Transactional(readOnly=false)
    public void delete(Question question) {
        super.delete(question);
    }
}
