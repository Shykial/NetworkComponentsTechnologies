package p.tul.applicationports.service;

import p.tul.domainmodel.entities.Client;

public interface UpdateAccountUseCase {
    Client updateClient(Client account);
}