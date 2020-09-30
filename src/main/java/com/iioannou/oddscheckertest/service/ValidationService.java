package com.iioannou.oddscheckertest.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author ioannou
 */
@Service
public class ValidationService {

    public boolean isInvalidOddValue(String oddValue) {

        return StringUtils.contains(oddValue, "0") ||
                (StringUtils.startsWith(oddValue, "SP") && StringUtils.length(oddValue) > 2)
                || StringUtils.countMatches(oddValue, "/") > 1
                || StringUtils.containsAny(oddValue, "*&#?!.+-");
    }
}
