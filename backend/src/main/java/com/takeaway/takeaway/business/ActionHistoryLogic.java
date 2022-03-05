package com.takeaway.takeaway.business;

import com.google.gson.Gson;
import com.takeaway.takeaway.dataaccess.model.ActionHistory;
import com.takeaway.takeaway.dataaccess.model.enums.ActionTypes;
import com.takeaway.takeaway.dataaccess.repository.ActionHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;

@Slf4j
@Component
@Transactional
public class ActionHistoryLogic {

    private final ActionHistoryRepository actionHistoryRepository;

    public ActionHistoryLogic(ActionHistoryRepository actionHistoryRepository) {
        this.actionHistoryRepository = actionHistoryRepository;
    }

    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null) {
            DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
            email = user.getClaims().get("email").toString();
        }
        return email;
    }

    public void AddHistoryRecord(ActionTypes type){
        AddHistoryRecord(type, null);
    }

    public void AddHistoryRecord(ActionTypes type, Map<String, Object> payload) {
        ActionHistory actionHistory = new ActionHistory();
        actionHistory.setType(type);
        actionHistory.setUserEmail(getAuthenticatedEmail());
        if (payload != null) {
            Gson gson = new Gson();
            String payloadJson = gson.toJson(payload);
            if (payloadJson.length() > 1000) {
                payloadJson = payloadJson.substring(0, 1000);
            }
            actionHistory.setPayload(payloadJson);
        }
        actionHistoryRepository.save(actionHistory);
    }
}
