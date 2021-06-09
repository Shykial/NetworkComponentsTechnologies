package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Client;

public interface UpdateAccountUseCase {
    Client updateClient(Client account);
}