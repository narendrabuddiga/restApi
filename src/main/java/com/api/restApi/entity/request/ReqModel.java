package com.api.restApi.entity.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ReqModel {

    private List<String> status;
}
