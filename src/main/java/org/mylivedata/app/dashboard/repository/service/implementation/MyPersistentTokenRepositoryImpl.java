package org.mylivedata.app.dashboard.repository.service.implementation;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.mylivedata.app.dashboard.domain.PersistentLoginsEntity;
import org.mylivedata.app.dashboard.repository.PersistentLoginsEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Created by lubo08 on 5.4.2014.
 */
public class MyPersistentTokenRepositoryImpl implements PersistentTokenRepository {

    @Autowired
    private PersistentLoginsEntityRepository persistentLoginsEntityRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {
        PersistentLoginsEntity rememberMeToken = new PersistentLoginsEntity();
        rememberMeToken.setLastUsed(new Timestamp(persistentRememberMeToken.getDate().getTime()));
        rememberMeToken.setToken(persistentRememberMeToken.getTokenValue());
        rememberMeToken.setUsername(persistentRememberMeToken.getUsername());
        rememberMeToken.setSeries(persistentRememberMeToken.getSeries());
        persistentLoginsEntityRepository.saveAndFlush(rememberMeToken);
    }

    @Override
    public void updateToken(String s, String s2, Date date) {
        PersistentLoginsEntity token = this.persistentLoginsEntityRepository.findBySeries(s);
        if (token != null){
            token.setToken(s2);
            token.setLastUsed(new Timestamp(date.getTime()));
            this.persistentLoginsEntityRepository.saveAndFlush(token);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String s) {
        PersistentLoginsEntity token = this.persistentLoginsEntityRepository.findBySeries(s);
        return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getToken(), token.getLastUsed());
    }

    @Override
    public void removeUserTokens(String s) {
        List<PersistentLoginsEntity> tokens = this.persistentLoginsEntityRepository.findByUsername(s);
        this.persistentLoginsEntityRepository.delete(tokens);
    }
}
