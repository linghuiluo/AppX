package com.github.appx.bl.services;

import java.util.Map;

import com.github.appx.api.Request;

public interface InputService {

    Request getInputParams(Map<String, Object> input);

}
