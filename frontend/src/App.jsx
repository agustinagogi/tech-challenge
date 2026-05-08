import { useState } from "react";

function App() {

  const [title, setTitle] = useState("");
  const [books, setBooks] = useState([]);
  const [userName, setUserName] = useState("");
  const [expectedReturnDate, setExpectedReturnDate] = useState("");
  const [message, setMessage] = useState("");

  async function searchBooks() {

    setMessage("");

    const response =
      await fetch(`http://localhost:8080/books/search?title=${title}`);

    const data = await response.json();

    setBooks(data);
  }

  async function borrowBook(bookId) {

    setMessage("");

    if (!userName || !expectedReturnDate) {
      setMessage(
        "Please enter user name and expected return date."
      );
      return;
    }

    const response = await fetch(
      "http://localhost:8080/loans",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          bookId,
          userName,
          expectedReturnDate,
        }),
      }
    );

    if (response.ok) {
      setMessage("Loan created successfully.");
      searchBooks();
    } else {
      const error = await response.json();

      setMessage(
        error.error || "Could not create loan."
      );
    }
  }

return (
    <main style={{ padding: "20px" }}>

      <h1>Library Search</h1>

      <input
        type="text"
        placeholder="Search book by title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />

      <button onClick={searchBooks}>
        Search
      </button>

      <h2>Borrow a book</h2>

      <label>User name</label>
      <br />

      <input
        type="text"
        placeholder="User name"
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
      />

      <br /><br />

      <label>Expected return date</label>
      <br />

      <input
        type="date"
        value={expectedReturnDate}
        onChange={(e) =>
          setExpectedReturnDate(e.target.value)
        }
      />

      <ul>
        {books.map((book) => (
          <li key={book.id}>
            {book.title}
            {" - "}
            Copies available:
            {" "}
            {book.availableCopies}

            <button
              disabled={book.availableCopies <= 0}
              onClick={() => borrowBook(book.id)}
              style={{ marginLeft: "10px" }}
            >
              Borrow
            </button>
          </li>
        ))}
      </ul>

      <p>{message}</p>

    </main>
  );
}

export default App;

