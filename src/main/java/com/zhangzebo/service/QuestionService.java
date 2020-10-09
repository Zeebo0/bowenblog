package com.zhangzebo.service;

import com.zhangzebo.dto.PaginationDTO;
import com.zhangzebo.dto.QuestionDTO;
import com.zhangzebo.exception.CustomizeErrorCode;
import com.zhangzebo.exception.CustomizeException;
import com.zhangzebo.mapper.QuestionMapperExt;
import com.zhangzebo.mapper.QuestionMapper;
import com.zhangzebo.mapper.UserMapper;
import com.zhangzebo.model.Question;
import com.zhangzebo.model.QuestionExample;
import com.zhangzebo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//  连接Question和User
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapperExt questionExtMapper;

    //  首页问题列表
    public PaginationDTO list(Integer page, Integer size) {
        //  paginationDTO用来存储分页需要的属性
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPages;
        //  获取总共的问题数
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        //  计算总共分页数
        if (totalCount % size == 0) {
            totalPages = totalCount / size;
        } else {
            totalPages = (totalCount / size) + 1;
        }

        //  防止用户输入超过总页码数的页码(但是sql没有修改，因此这个就是个空壳，待完善)
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        //  计算总共分页数
        paginationDTO.setPagination(totalPages, page);

        Integer offset = size * (page - 1);
        //  每一页显示的页码集合
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        //  创建一个QuestionDTO集合
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //  通过id获取user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //  将question中的值复制到questionDTO中
            BeanUtils.copyProperties(question, questionDTO);
            //  把上面获取到的user添加到questionDTO中
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    //  我的提问问题列表
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        //  paginationDTO用来存储分页需要的属性
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPages;
        //  获取总共的问题数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        //  计算总共分页数
        if (totalCount % size == 0) {
            totalPages = totalCount / size;
        } else {
            totalPages = (totalCount / size) + 1;
        }

        //  防止用户输入超过总页码数的页码(但是sql没有修改，因此这个就是个空壳，待完善)
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        //  计算总共分页数
        paginationDTO.setPagination(totalPages, page);

        Integer offset = size * (page - 1);
        //  每一页显示的页码集合
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        //  创建一个QuestionDTO集合
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //  通过id获取user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //  将question中的值复制到questionDTO中
            BeanUtils.copyProperties(question, questionDTO);
            //  把上面获取到的user添加到questionDTO中
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    //  判断数据库中是否存在这个问题
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //  第一次创建这个问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            //  更新这个问题
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //  浏览次数功能
    //  每次访问这个问题，问题的浏览次数就加一
    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
