package com.github.appx.bl.services;

import com.github.appx.api.Request;

import java.util.Map;

public interface InputService {

    Request getInputParams(Map<String, Object> input);

}
