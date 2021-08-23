// тестовые данные в виде массивов (заменяют таблицы БД)

import {Author} from '../model/Author';
import {Book} from '../model/Book';
import {Review} from '../model/Review';

export class TestData {

  static books: Book[] = [
    {
      id: 1,
      name: '111',
      publisher: 'one 1111 one',
      yearPublisher: 2001,
      isbn: 1111,
      rating: 1,
      authors: [{id: 1, firstname: 'one', secondName: 'one second name'}, {
        id: 4,
        firstname: 'four',
        secondName: 'four second name'
      }]
    },
    {
      id: 2,
      name: '222',
      publisher: 'two 2222 two',
      yearPublisher: 2002,
      isbn: 2222,
      rating: 2,
      authors: [{id: 2, firstname: 'four', secondName: 'two second name'}, {
        id: 3,
        firstname: 'three',
        secondName: 'three second name'
      }]
    },
    {
      id: 3,
      name: '333',
      publisher: 'three 333333 three',
      yearPublisher: 2003,
      isbn: 3333,
      rating: 3,
      authors: [{id: 3, firstname: 'three', secondName: 'three second name'}]
    },
    {
      id: 4,
      name: '444',
      publisher: 'four 44444 four',
      yearPublisher: 2004,
      isbn: 4444,
      rating: 5,
      authors: [{id: 4, firstname: 'four', secondName: 'four second name'}]
    },
    {
      id: 5,
      name: '555',
      publisher: 'five 5 five',
      yearPublisher: 2005,
      isbn: 5555,
      rating: 2,
      authors: [{id: 4, firstname: 'four', secondName: 'four second name'}, {
        id: 2,
        firstname: 'four',
        secondName: 'two second name'
      }]
    },
    {
      id: 6,
      name: '666',
      publisher: 'six 66666 six',
      yearPublisher: 2006,
      isbn: 6666,
      rating: 4,
      authors: [{id: 3, firstname: 'three', secondName: 'three second name'}, {
        id: 4,
        firstname: 'four',
        secondName: 'four second name'
      }]
    },
  ];


  static authors: Author[] = [
    {id: 1, firstname: 'one', secondName: 'one second name'},
    {id: 2, firstname: 'four', secondName: 'two second name'},
    {id: 3, firstname: 'three', secondName: 'three second name'},
    {id: 4, firstname: 'four', secondName: 'four second name'},
  ];


  static reviews: Review[] = [
    {
      id: 1,
      rating: 2,
      comment: "1111",
      commenterName: '1111   11111',
      book: {
        id: 1,
        name: '111',
        publisher: 'one 1111 one',
        yearPublisher: 2001,
        isbn: 1111,
        rating: 1,
        authors: [{id: 1, firstname: 'one', secondName: 'one second name'}, {
          id: 4,
          firstname: 'four',
          secondName: 'four second name'
        }]
      }
    },

    {
      id: 2,
      rating: 2,
      comment: "222",
      commenterName: '2222   22222',
      book: {
        id: 2,
        name: '222',
        publisher: '222 66666 2222',
        yearPublisher: 2001,
        isbn: 1111,
        rating: 1,
        authors: [{id: 1, firstname: 'one', secondName: 'one second name'}, {
          id: 4,
          firstname: 'four',
          secondName: 'four second name'
        }]
      }
    },
  ];

}

