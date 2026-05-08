async function searchBooks() {

    const title =
        document.getElementById("titleInput").value;
    const results =
        document.getElementById("results");
    const message = document.getElementById("message");

    results.innerHTML = "";
    message.textContent = "";

    const response =
        await fetch(`/books/search?title=${title}`);

    const books = await response.json();

    books.forEach(book => {
        const li = document.createElement("li");

        li.textContent =
            `${book.title} - Copies available: ${book.availableCopies}`;

        const button = document.createElement("button");
        button.textContent = "Borrow";
        button.disabled = book.availableCopies <= 0;

        button.onclick = () => borrowBook(book.id);

        li.appendChild(button);
        results.appendChild(li);
    });

}

async function borrowBook(bookId){
    const userName = document.getElementById("userNameInput").value;
    const expectedReturnDate = document.getElementById("expectedReturnDateInput").value;
    const message = document.getElementById("message");

    message.textContent = "";

    if (!userName || !expectedReturnDate){
        message.textContent = "Please enter user name and expected return date"
        return;
    }

    const response = await fetch("/loans", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                bookId: bookId,
                userName: userName,
                expectedReturnDate: expectedReturnDate
            })
        });

        if (response.ok) {
            message.textContent = "Loan created successfully.";
            searchBooks();
        } else {
            const error = await response.json();
            message.textContent = error.error || "Could not create loan.";
        }
}