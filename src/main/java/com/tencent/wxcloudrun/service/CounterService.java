package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counter;

import java.util.Optional;
import java.util.List;

public interface CounterService {

  //jiayou
  Counter getCounter(Integer id);

  void upsertCount(Counter counter);

  void clearCount(Integer id);
}
