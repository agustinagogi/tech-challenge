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

}

