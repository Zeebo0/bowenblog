package com.zhangzebo.service;

import com.zhangzebo.dto.NotificationDTO;
import com.zhangzebo.dto.PaginationDTO;
import com.zhangzebo.dto.QuestionDTO;
import com.zhangzebo.enums.NotificationStatusEnum;
import com.zhangzebo.enums.NotificationTypeEnum;
import com.zhangzebo.exception.CustomizeErrorCode;
import com.zhangzebo.exception.CustomizeException;
import com.zhangzebo.mapper.NotificationMapper;
import com.zhangzebo.mapper.UserMapper;
import com.zhangzebo.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        //  paginationDTO用来存储分页需要的属性
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPages;
        //  获取总共的问题数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
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
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        //  创建一个QuestionDTO集合
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver() != user.getId()) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
