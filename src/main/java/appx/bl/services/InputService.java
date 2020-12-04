package appx.bl.services;

import java.util.Map;

import appx.api.Request;

public interface InputService {

    Request getInputParams(Map<String, Object> input);

}
