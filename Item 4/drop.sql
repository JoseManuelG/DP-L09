start transaction;

use `Acme-BnB`;

revoke all privileges on `Acme-BnB`.* from  'acme-user'@'%' ;
revoke all privileges on `Acme-BnB`.* from  'acme-manager'@'%';

drop user 'acme-user'@'%' ;
drop user 'acme-manager'@'%' ;

drop database `Acme-BnB` ;


commit;