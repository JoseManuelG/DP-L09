start transaction;

use `Acme-CnG`;

revoke all privileges on `Acme-CnG`.* from  'acme-user'@'%' ;
revoke all privileges on `Acme-CnG`.* from  'acme-manager'@'%';

drop user 'acme-user'@'%' ;
drop user 'acme-manager'@'%' ;

drop database `Acme-CnG` ;


commit;