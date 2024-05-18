package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CountersMapper;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class CounterServiceImpl implements CounterService {

  final CountersMapper countersMapper;

  public CounterServiceImpl(@Autowired CountersMapper countersMapper) {
    this.countersMapper = countersMapper;
  }

  @Override
  public Counter getCounter(Integer id) {
    return countersMapper.selectById(id);
  }

  @Override
  public void upsertCount(Counter counter) {
    int i = countersMapper.updateById(counter);
    if(i <= 0){
      countersMapper.insert(counter);
    }
  }

  @Override
  public void clearCount(Integer id) {
    countersMapper.deleteById(id);

  }
}
