USE `dancestudio_db`;
delimiter //
CREATE PROCEDURE validMembershipByClient(param BIGINT)
begin 
SELECT membership.id, membership.start_date, membership.end_date, membership.balance_quantity, membership.type_of_membership_id FROM membership WHERE membership.client_id = param AND (membership.balance_quantity > 0 OR membership.balance_quantity IS NULL) AND membership.end_date >= CURRENT_DATE();
end//
delimiter;