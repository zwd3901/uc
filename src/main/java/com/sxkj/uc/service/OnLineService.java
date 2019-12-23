package com.sxkj.uc.service;

import com.sxkj.common.base.BaseService;
import com.sxkj.uc.dao.OnLineDao;
import com.sxkj.uc.entity.OnLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 */
@Service
@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OnLineService extends BaseService<OnLine> {
    @Autowired
    private OnLineDao onLineDao;

    /**
     * 根据用户id删除记录
     *
     * @param userId
     * @return
     */
    public void deleteToken(String userId) {
        OnLine onLine = new OnLine();
        onLine.setUserId(userId);
        List<Map<String, Object>> list = onLineDao.findList(onLine);
        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String id = map.get("id").toString();
                onLine = new OnLine();
                onLine.setId(id);
                onLineDao.deleteByPrimaryKey(onLine);
            }
        }
    }
}
