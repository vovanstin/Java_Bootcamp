import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
	private TransactionNode head = null;
	private Integer count = 0;
	
	@Override
	public void addTransaction(Transaction transaction) {
		TransactionNode tmp = new TransactionNode(transaction);
		
		if (head != null) {
			head.setPrev(head);
		}
		tmp.setPrev(null);
		tmp.setNext(head);
		head = tmp;
		count++;
	}
	
	@Override
	public void removeTransaction(UUID id) throws TransactionNotFoundException {
		TransactionNode tmp = head;

		for (int i = 0; i < count; i++) {
			if (tmp.getData().getId() == id) {
				if (tmp.getNext() != null) {
					tmp.getNext().setPrev(tmp.getPrev());
				}
				if (tmp.getPrev() == null) {
					head = tmp.getNext();
				} else {
					tmp.getPrev().setNext(tmp.getNext());
				}
				count--;
				return ;
			}
			tmp = tmp.getNext();
		}
		throw new TransactionNotFoundException();
	}

	@Override
	public Transaction[] toArray() {
		TransactionNode tmp = head;
		Transaction[] array = new Transaction[count];
		
		if (count == 0 || head == null) {
			return (null);
		}
		for (int i = 0; i < count; i++) {
			array[i] = tmp.getData();
			tmp = tmp.getNext();
		}
		return (array);
	}
}

class TransactionNode {
    private Transaction data;
    private TransactionNode next;
    private TransactionNode prev;

    public TransactionNode(Transaction data) {
        this.data = data;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public TransactionNode getNext() {
        return next;
    }

    public void setNext(TransactionNode next) {
        this.next = next;
    }

    public TransactionNode getPrev() {
        return prev;
    }

    public void setPrev(TransactionNode prev) {
        this.prev = prev;
    }
}
