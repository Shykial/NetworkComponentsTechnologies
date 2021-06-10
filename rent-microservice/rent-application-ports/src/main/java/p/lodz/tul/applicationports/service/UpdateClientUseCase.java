package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Client;

public interface UpdateClientUseCase {
    Client updateClient(Client account);
}