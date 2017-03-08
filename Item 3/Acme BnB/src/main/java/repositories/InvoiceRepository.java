
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

	//Returns the minimum number of invoices per actor
	@Query("select min(t.invoices.size) from Tenant t")
	public Integer getMinimumInvoicesPerTenant();

	//Returns the average number of invoices per actor
	@Query("select avg(t.invoices.size) from Tenant t")
	public Double getAverageInvoicesPerTenant();

	//Returns the maximum number of invoices per actor
	@Query("select max(t.invoices.size) from Tenant t")
	public Integer getMaximumInvoicesPerTenant();

	//Returns the total amount of money due in the invoices issued by the system
	@Query("select sum(b.totalAmount) from Book b where b.invoice is not null")
	public Double getTotalDueMoneyOfInvoices();

}
