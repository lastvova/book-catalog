insert into authors (id, first_name, second_name)
values (1, 'Leo', 'Tolstoy'),
       (2, 'William', 'Shakespeare'),
       (3, 'Herman', 'Melville'),
       (4, 'Fyodor', 'Dostoyevsky'),
       (5, 'John Ronald Reuel', 'Tolkien'),
       (6, 'Dan', 'Brown'),
       (7, 'Stephen', 'Hawking'),
       (8, 'Frank', 'Herbert'),
       (9, 'Joanne K', 'Rowling'),
       (10, 'Scott', 'Fitzgerald'),
       (11, 'Rick', 'Astley');

insert into books (id, name, year_publisher, isbn, publisher)
values (1, 'War and Peace', 2009, 1427038651124, 'Read How You Want'),
       (2, 'Anna Karenina', null, 9780553210347, 'Bantam Books'),
       (3, 'Hamlet', 2005, 1420922531221, 'Digireads'),
       (4, 'King Lear', 2018, 9781980765707, 'Independently published'),
       (5, 'Moby Dick', 1993, 9781566193566, 'Barnes & Noble'),
       (6, 'Crime and Punishment', 2011, 1613821824141, 'Simon  Brown'),
       (7, 'The Lord of the Rings', 2021, 9780358653035, 'Mariner Books'),
       (8, 'The Silmarillion', 2013, 9780007523221, 'Houghton Mifflin'),
       (9, 'The Hobbit Or There and Back Again', 2001, 9780618150823, 'Young Readers Paperback Tolkien'),
       (10, 'Inferno', 2011, 1234567890124, 'Timon Pumba'),
       (11, 'A Brief History of Time Stephen Hawking', null, 9780553380163, 'paperback'),
       (12, 'Rickroll', null, 3263463463434, 'Rickroll corporation'),
       (13, 'Harry Potter and the Sorcerer Stone', null, 3262362362362, 'afgds'),
       (14, 'Dune', null, 1531351351352, 'fdha');

insert into authors_books (author_id, book_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 5),
       (4, 6),
       (5, 7),
       (5, 8),
       (5, 9),
       (6, 10),
       (7, 9),
       (7, 11),
       (8, 14),
       (9, 13),
       (11, 12);

insert into book_catalog.reviews (id, commenter_name, comment, rating, book_id)
values (1, 'Adam', 'Would absolutely recommend to any Tolkien fan or fan of fantasy/literature in general.', 5, 8),
       (2, 'Demonsub',
        'It’s by far my favourite book of the year so far and I certainly look forward to rereading it in years to come.',
        5, 7),
       (3, 'Adam', 'Would absolutely recommend to any Tolkien fan or fan of fantasy/literature in general.', 5, 8),
       (4, 'Nick', 'Not bad', 3, 1),
       (5, 'Anna', 'not my', 2, 1),
       (6, 'Andy', 'I am very happy to have read this book', 5, 1),
       (7, 'Samanta', 'Not so good', 3, 1),
       (8, 'John', 'Good', 4, 2),
       (9, 'Gerald', 'didnt like', 2, 2),
       (10, 'Lola', 'Good', 5, 2),
       (11, 'Steve', 'fooo', 1, 2),
       (12, 'Andrew', 'nice', 4, 3),
       (13, 'Maks', 'i like it', 5, 3),
       (14, 'Paul', '----', 4, 3),
       (15, 'Rose', 'I enjoyed this book', 5, 3),
       (16, 'Vivien', 'not my', 1, 3),
       (17, 'Lilian', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In accumsan elementum sem, eu ornare neque dapibus eget. Duis pellentesque malesuada lacus nec semper. Donec ut urna a lorem consequat elementum et vitae libero. Sed in condimentum neque. Integer non dictum massa. Interdum et malesuada fames ac ante ipsum primis in faucibus. Mauris ornare quis tellus maximus tincidunt. Praesent eget maximus mi, eget ultricies felis. Proin laoreet euismod nunc, ac hendrerit ipsum pulvinar a. Fusce iaculis auctor ullamcorper. Donec gravida scelerisque lorem ac luctus. In id ipsum in libero eleifend ullamcorper. Vestibulum porta, nibh vel ornare luctus, quam lacus facilisis ex, vitae commodo urna ex a neque. Pellentesque gravida gravida enim sed lobortis.

Suspendisse vitae efficitur ex. Sed velit nunc, pellentesque a felis vel, facilisis sagittis dolor. Nunc tincidunt accumsan interdum. Donec fermentum mauris ipsum, et scelerisque lorem venenatis vitae. Integer vel augue ac felis hendrerit faucibus at et metus. Etiam elementum nibh lacus, a convallis erat pellentesque sed. Vivamus non pretium nunc. Suspendisse ullamcorper iaculis felis, eu egestas ligula tempus eu. Vestibulum tincidunt sodales felis. Integer faucibus libero sit amet risus volutpat, a laoreet odio molestie.

Quisque malesuada est ac lacus auctor ullamcorper. Aenean vitae elementum nisl. Nullam rutrum erat nisl, quis ornare nunc aliquam quis. Vestibulum facilisis tincidunt ex, ut lacinia nibh egestas eget. Vivamus eros turpis, sollicitudin ac tincidunt vel, gravida id lorem. Morbi quis eros quis tellus egestas suscipit. Fusce congue, leo vel scelerisque dignissim, nulla nunc facilisis tellus, ac imperdiet sem lorem vel nunc. In at purus condimentum, mattis turpis vitae, fringilla lectus.

Etiam et mi et felis finibus luctus at quis orci. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Praesent faucibus, neque at aliquam rutrum, ipsum ipsum feugiat est, non lobortis diam ex id magna. Nunc condimentum vulputate dui eu faucibus. Aliquam dignissim pulvinar odio, ut euismod ligula. Aenean pharetra mi orci, vitae consectetur orci vestibulum sed. Fusce consequat sit amet libero ac interdum. Suspendisse sed sollicitudin orci. Etiam euismod fringilla consequat. Nunc pretium congue dui id volutpat. Maecenas tincidunt viverra felis, ac varius augue porta sit amet.

Integer tempor, turpis id vulputate posuere, velit risus mollis mauris, eget gravida risus magna quis mauris. Etiam ullamcorper at velit eu fringilla. Morbi facilisis porta nisl eget ultrices. Fusce a enim sed mauris tempor suscipit. Quisque viverra faucibus justo ut porttitor. Nam tellus augue, pellentesque sit amet erat eget, lacinia molestie felis. Sed suscipit augue quam, ut venenatis elit lacinia at. Nunc ultrices ipsum metus. Donec ac felis feugiat, accumsan purus at, dictum quam.',
        4, 4),
       (18, 'Pit',
        'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using ''Content here, content here'', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for ''lorem ipsum'' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).',
        5, 4),
       (19, 'Megan', 'In an alternate America where the Civil War stretches on, a former Confederate spy turned Pinkerton agent and an escaped slave turned dirigible captain may be pursuing the man -- or are they both being tricked?

This book could have been a lot better with an extra few dozen pages. The characters and setting were interesting (although I was disappointed not to meet again with Briar and Ezekiel from the first volume), but the villains and their Evil Plot were introduced so late and briefly that it was quite anticlimactic, despite some decent gun fights and air battles.

One of the best-done aspects of the world-building was the unusual wealth of sensory details. In too many steampunk novels the technology seems too magical, too clean and easy. Here you get a good sense of the difficulty of wrestling with heavy, unwieldy contraptions while wearing bulky, ill-fitting protective gear, while gunpowder stings your eyes and nose and smoke obscures your vision.',
        3, 4),
       (20, 'Ginger', 'Clementine is a slim book, a fun romp, but in some ways unsatisfying, and in others, a little...troublesome. Which is perhaps too much thought to put into a book that seems to be intended as brain candy and little more. But still, doubts remain. Can you really keep slavery as an aspect of life in the Southern states during the Civil War, and yet then try to make the pre-eminent Confederate spy''s motives all about states rights, and give her absolutely no prejudices against an escaped black slave?

Note: The rest of this review has been withheld due to the changes in Goodreads policy and enforcement. You can read why I came to this decision',
        3, 4),
       (21, 'Rick Astley', 'Clementine is a novel set in Cherie Priest''s Clockwork Century world. It was entertaining but not as good as Dreadnought. Heck, it wasn''t even as good as Boneshaker. Fortunately, it bears no connection to either of those novels and you can go ahead and skip it if you want to.

I really like the production of this audiobook. I like how they used a female narrator for the female protagonist''s chapters and a male narrator for the male protagonist''s chapters. It really got to be a lot of fun when the two came together and Dina Pearlman would do Belle Boyd''s dialogue in Captain Hainey''s chapters and vice-versa. Unfortunately, the story was pretty weak, and I found it fairly implausible.

If you''ve already read Boneshaker, just go ahead to Dreadnought and don''t worry about this one unless you just can''t get enough of The Clockwork Century.',
        5, 5),
       (22, 'Cherie', 'On the surface, the action in this story was a little too easy and the plot very simple, but I enjoyed it! It was fun.

I liked the characters, even if they were a little shallow. They had heart.', 5, 5),
       (23, 'Brick',
        'My sister read and wrote a review of Clementine before I got to it, so I noticed things that she mentioned in her review that I wouldn''t normally notice: It was a fast-paced book; it lacked the sharing of background and history that Boneshaker had; it could probably be called more superficial. But I don''t mind. Sometimes, a slow, leisurely exploration of a new place or culture or universe is enjoyable, the way curling up in front of the fire with a cup of hot chocolate and a book on a snowy winter afternoon is enjoyable. But Clementine is not fire and cocoa and book on a snowy day; Clementine is more like when we were kids driving with my dad, and he would take the corners as fast as he dared (which ended up never being more than the speed limit, apparently), pressing us all against doors or floating us into the space between seats, suspended in the middle of things (depending on which side of the car we were on). Clementine is brief and maybe lacking depth, but that doesn''t make it less fun.',
        4, 5),
       (24, 'Carnegie',
        'This is one of the best thrillers I''ve ever read! The characters are very well-developed. Good people doing good things for good reasons. This was a great read, which I thoroughly enjoyed and will certainly read this author again. Felt like the tech in this book was reaching for Brett Arquette''s technothrillers.',
        4, 10),
       (25, 'Jane', 'CHAPTER 1

Obscure reviewer Jane Steen sat in her modest study in cozy suburban Illinois and stared with horror at the object she held in her hands. Measuring nine-and-a-half by six-and-a-quarter by one-and-a-half inches, the object was encased in a shiny substance the overweight reviewer knew to be plastic.

A book of some kind.

To the little known reader’s brilliant mind and eidetic memory, identifying the book was a simple task. The labels affixed to the spine proclaimed its origin: the library. It was adorned with the terrifying profile of a red-cheeked man in a red cap and red cloak, surmounted by a series of concentric circles.

Red . . . The color of blood. And those circle things look like a target.

The reviewer’s hands trembled as her fingers traced the bold lettering on the book’s cover. “DAN BROWN . . . INFERNO.”

I have to review this?!

The reviewer knew that Dan Brown (born June 22, 1964) is an American author of thriller fiction who is best known for the 2003 bestselling novel, The Da Vinci Code. Brown''s novels are treasure hunts set in a 24-hour period, and feature the recurring themes of cryptography, keys, symbols, codes, and conspiracy theories. His books have been translated into 52 languages, and as of 2012, sold over 200 million copies. Two of them, The Da Vinci Code and Angels & Demons, have been adapted into films.

I copied that straight out of Wikipedia.

CHAPTER 2

I am holding Inferno by Dan Brown and I have to review it, the plump, somewhat scruffily dressed, middle-aged woman recapped. Terror made her nauseous, but she bravely looked at her Goodreads updates to refresh her memory, reading the scathing comments she had left only days ago on the popular readers’ Web site.

Dan Brown is going to kill me!

The female reviewer recalled that Dan Brown is currently the twentieth highest selling author of all time and with only six books, he has achieved these sales writing fewer books than anyone above him on the list. The Robert Langdon series is currently the seventh highest selling series of all time.

Like Dan Brown, I do most of my research on the Web. Not the Internet. Dan Brown likes to talk about the Web. It sounds more . . . spidery.

The married reviewer felt an instant spark of attraction toward the sandy-haired author, who always seems to be wearing a tweed jacket in his photo shoots.

Could he be Robert Langdon in disguise?

CHAPTER 3

Overreacting wildly, the obscure critic overreacted for a few minutes, then got a grip on herself and scanned her updates. She noted that renowned author Dan Brown tends to get his tenses confused, loves to put identifiers in front of his characters’ names, and is inordinately fond of ellipses and loud punctuation such as exclamation points, question marks and interrobangs.

Why is that?!

Oh yes, and he loves italics, which pop up all over the place, not always readily identifiable with one particular character.

CHAPTER 4

The practically unknown reviewer picked up her copy of Inferno by Dan Brown, scanning its mysterious cover with the picture of the sage she now knew to be internationally famous poet Dante (c. 1265–1321), who was a major Italian poet of the Middle Ages. His Divine Comedy, originally called La Comedia and later called Divina by Boccaccio, is widely considered the greatest literary work composed in the Italian language and a masterpiece of world literature.

Gad, I love Wikipedia.

She remembered that bestselling author Dan Brown frequently recaps the previous action near the beginning of a chapter, and that his bestselling prose is scattered with information dumps so densely constituted that they resemble the excreta of the famed Friesian horse, a creature mentioned in the bestselling novel Inferno.

The reviewer’s eidetic memory roamed over the plot. She recalled that Robert Langdon, granite-jawed Harvard professor of symbology and art historian specializing in iconography, wakes up in Florence to find that he remembers nothing, people are apparently trying to kill him, and he is carrying a suggestively shaped container that contains a mysterious object. He is helped by pretty blonde ponytailed genius-IQd Sienna Brooks, who has the hots for him. And his confused memories recall a mysterious silver-haired attractive older woman who wants him to seek and find, and who undoubtedly will have the hots for him too.

Meanwhile, on the mysterious ship The Mendacium, facilitator Knowlton has just watched a video that is more terrifying than the most terrifying thing you can possibly imagine.

Dan Brown is fond of making his characters react with terror in the hope that the reader will also be terrified?

What is this book?!

CHAPTER 5

“Ah yes!” the clinically obese woman derided, not knowing that “deride” must have an object. She recalled that most of the plot of Inferno consisted of Langdon and Sienna running around famous tourist spots finding clues, while being chased by a leather-clad woman who turns out to be superfluous to the plot, a bleeding strangely dressed man who also, honestly, didn’t have much of a role except to increase dramatic tension, and some black-clad soldiers who weren’t really necessary either, except that they get to do all the dirty work like good little minions. As they pass various monuments, Langdon recalls large indigestible lumps of architectural and historical detail.

As the story lumbers to its end it picks up speed, with one quite nice bit of misdirection but otherwise the usual thriller fare of all the important stuff being packed into the last few pages so that the reader feels like a lot went on.

And then there was the ending . . .

CHAPTER 6

“I was outraged,” the reviewer recalled, outraged. How could everyone suddenly decide that the Evil Plan may, in fact, be a Jolly Good Thing? Why was the Evil Villain’s Number One not banged up in jail but instead allowed to work for the good guys?

And didn’t Dan Brown think through what he was proposing as Quite A Good Thing, Really?!

The reviewer ran her hands over the shiny cover of the bestselling novel Inferno by Dan Brown. She recalled that Langdon rides off smugly into the sunset of a brand new world without any thought for the social, economic, and religious consequences of what just happened. Not to mention the fact that a small bunch of white people take it upon themselves to re-engineer the fate of mankind without consulting the rest of the world.

And that’s supposed to be OK because they’re white, rich, and brilliant.

CHAPTER 7

The overweight woman gnashed her teeth dramatically and then, like renowned professor of symbology Robert Langdon, decided to settle down with a good book. Sensing it was time to wrap up her interminable review, there was one thought that still haunted her.

Dan Brown knows exactly what he’s doing.

The frequent recaps so the reader doesn’t lose his way . . . the italics that also serve as simplified reminders of what’s going on . . . the way the action takes place in tourist spots that are easily visited and quite easy to research . . . the very short chapters . . . the dropping of brand names . . .

He’s manipulating the Baby Boomers!?!

The reviewer realized that for an audience accustomed to a diet of CSI and the Discovery Channel, Dan Brown’s storytelling style is accessible and informative. Used to being given the potted version of history by talking heads as the camera zooms around in a dizzying series of filler shots, the average reader of Brown’s books will sink into a TV-induced-like stupor and, instead of thinking about the plot or the writing, will simply enjoy the experience and come back for more.

And that, thought the reviewer, is why Dan Brown is the novelist of the future.

Sensing it was time, really, to revert to a state of denial before that last thought took hold in her brain, the reviewer took one last look at the cover of the bestselling novel Inferno and sighed.

I can return it to the library and forget this ever happened . . .', 5, 10),
       (26, 'Deska', 'I really enjoyed this book. Thou personally I think it''s a bit different than the other previous three books. The other three books have similarities in having a story plot that creating a really blur line between history and fiction. But in this fourth book, the history is like the inspiration of the fiction story, but I still liked it and gave it 3.5 stars.

I really enjoyed the thrill and excitement of Langdon adventure. And as a former international security student, I have an understanding regarding on security threats and this book is really interesting especially in that part. We all know about biological weapon and act of terrorism, but this book offers something that I haven''t thought about before regarding on that issue. And it is so exciting.

Overall, it is such an enjoyable read and very easy to digest. Thou it''s not amazing, but still worth to read. :)

-----------------------------------------------------

I am just wishing that this book will be better than The Lost Symbol.. That one was a major fail of the series.. Hope it''ll be amazing..',
        4, 10),
       (27, 'Willow', 'This is my first Dan Brown book and what can I say...it pretty much sucked.

I was kind of shocked. Yes, I had read a lot of disparaging comments about Brown’s writing, but I pushed them aside, figuring his books must be at least entertaining. Otherwise, why would he be so popular? And I rather like cheesy books. This one had a condescending tone though that grated on my nerves and sapped all the fun right out of the story. It was a tedious read.

First off, Brown’s characters are boring. There’s no depth or nuisance. Everybody talks and thinks alike. Their dialogue has no individuality. There are no intricate, personality conflicts. Brown also has the annoying tendency to tell you how brilliant and amazing his characters are ALL THE TIME, but he never really shows you why they are extraordinary.

Then there are the endless info dumps. OMG! Brown gives a Humanities lecture for every museum Langdon goes to (even when his characters are running for their lives). They’re not short little vignettes either that give character and life to a place. No, they’re long dry passages that seem to be cut and pasted straight from a travel brochure. Brown will use half a page to describe a statue that has NOTHING to do with the plot. I found his description on Botticelli’s Map of Hell to be somewhat questionable too.

My God! Langdon’s hand trembled slightly as he absorbed the macabre scene projected on the wall before him. No wonder I’ve been seeing images of death.

At his side, Sienna covered her mouth and took a tentative step forward, clearly entranced by what she was seeing.

The scene projected was a grim oil painting of human suffering—thousands of souls undergoing wretched tortures in various levels of hell. The underworld was portrayed as a cutaway across section of the earth into which plunged a cavernous funnel-shaped pit of unfathomable depth. This pit of hell was divided into descending terraces of increasing misery, each level populated by tormented sinners of every kind. Dark, grim, and terrifying … Botticelli had crafted his Map of Hell with a depressing palate of reds, sepias, and browns.”



What the hell is Brown talking about? The people are teeny weeny! How could Langdon and Sienna even see them? Yes I know it’s a nit. But it made me wonder… were all of Brown’s boring info dumps crap? They better not be, damn it! (To be honest, I didn’t bother to check). But if you’re going bore the snot out of me, at least make sure you’re boring me with accurate information.

The plot is probably the best part of this book. There were some twists and turns I didn’t see coming, and Brown practically ends every chapter in a cliff hanger, so the book kept moving. There are so many plot holes though, it was like a sponge. If you think too much about it, you’ll spend all your time rolling your eyes and fall out of your chair.

In the end, I’m amazed that Brown is a bestselling author. His writing is terrible. He tells instead of shows. He repeats everything at least twice, sometimes three or four times. He describes three amazing European cites, but doesn’t bring any of them to life. And his story starts up an interesting conversation about population and the apocalypse, but Brown never gives it any real thought. The ending was so sanctimonious and preachy, I wanted to toss the book across the room. Maybe without the book’s snooty tone, this could have been a fun and cheesy read, but Brown takes himself way too seriously.


I give 1 ½ stars.

Are all Brown’s books this bad?', 2, 10),
       (28, 'David', 'I''ve spent the past few years writing a book on Inferno, immersing myself in Dante''s Commedia, and was looking forward to a fun read. While the book is decent it''s still a disappointment. Despite having quite a few issues with Da Vinci Code, it was nevertheless a fun trip through the world of art and puzzle solving. Unfortunately Inferno reads more like a movie treatment with some factoids thrown in. At times it reminded me of the old Batman episodes with the caped crusader pulling off an implausible escape from the bad guys every 5 minutes.

On the plus side, Brown does give Dante a pretty good treatment for the reader not familiar with the Commedia. I also enjoyed the whirlwind tour of the city (having visited Florence last year). Inferno continues to follow Brown''s style of page-turning, very short chapters and I have to admit I got sucked in.

On the minus side, the book is replete with the usual anti-Catholic bigotry (although not as bad as Da Vinci Code). And of course, there''s the writing. Brown''s writing isn''t as bad as some of the critics say (including some readers of this site), but Brown certainly isn''t writing literary fiction either.

I''m not a Brown hater, but I have to wonder what Dante''s Inferno would have looked like in the hands of Umberto Eco.',
        3, 10),
       (29, 'Desire', 'I considered giving this two stars but then I realized there really wasn''t anything I liked about it. It was so boring and it took me more than a week to get through. I know it''s too much to ask that Dan Brown scale the heights of Angels & Demons or even The Da Vinci Code once again but I''m somewhat baffled as to how his writing has taken a turn for the worse and it started with that blasted The Lost Symbol.

There''s nothing much to say about this to be honest. The plot was kind of intriguing mostly because it raised ethical questions and provided food for thought but the characters were so underdeveloped and felt really flat. As a result of this, I wasn''t invested in any of them or the story. It all just plodded along with me quirking an eyebrow here and there and wondering when it was going to end. I certainly don''t understand why it took so many pages to tell this very mediocre story. As it usually goes with Dan Brown books this read like a tour of ancient buildings in ancient cities and while these would have been great to explore on a tour in person or even read in a guide book, Brown does a terrible job of interweaving the story and these ''attractions''

Would I recommend this to anyone? Not really. There''s nothing to see here so I would encourage you to keep it moving.',
        1, 10),
       (30, 'Jason',
        'To all the folks who rate a book before they''ve read it, skewing the ratings for everyone...go to Amazon with all the other flamers. Leave our ratings alone.',
        3, 10),
       (31, 'Mario', 'Founding a genre like a boss

Stealing everything possible from mythology and the, maybe sometimes a tiny bit boring, old, classics.
The beloved tradition of using others'' ideas to create something new is big here, especially because Tolkien had the perfect background to milk everything from wherever he could find inspirations, from ancient to medieval and, at the time, modern works. It would especially be interesting to read or reread LotR with a focus on how he let the classics mutate to new forms, transformed oldfashioned tropes to fit for a modern audience, and especially made it a compelling, well written, and suspenseful pageturner. Don´t be angry, good old classics, it´s not your fault, your poor creators just had no creative writing courses available or were hunted by the inquisition, or it were total monopolies to that their works were the only ones available, and thereby never cared about royalties, book signing tours, or target audiences.

Black, white, and the most important grey
The pure, camouflages fascistic, evil, is of course as noir as possible, but especially the sexy seductiveness of the mind penetrating psi magic of the distilled badassery, is one of the main driving engines of the groundbreaking epic journey, because good old almightiness totally corrupts. It´s just normal that everyone is struggling with the whispering of the dark side with all its attractive options and the real life implications of this are, well, terrible, frustrating, and daunting. Throw money at close to everyone and she/he will get corrupted, especially if the alternative is to get eaten by orcs while the family is raped by Uruk hais and Balrogs.

Establishing cliffhangery ends of single parts
One just can´t stop, this damn, evil tendency of the genre to stop at the most suspenseful part and let the reader hanging to wait for felt eternities. As if Sauron wasn´t bad enough, this vicious cycle continues with each new, far too multi k page series and eats away the lives of poor, innocent humans, not to speak of their tormented souls that can´t find peace over these nauseating periods of despair and regret to have been relapsing. Again! I´m not sure if Tolkien should be praised or damned for having laid the foundations for things like Sandersons´, Jordans´, Eriksons´, etc. amazingly exhausting and immersive monster series. I´m ashamed to admit it, but I have the whole, good old second hand paperback, Wheel of time series ‚(and the new ones) lying around and I am afraid to restart reading the whole thing (not just the first few parts like a few years ago), because I fear that it could trigger reading and rereading other series and finally Wheel of time again until 2027 or something, not just having lost contact to reality (not much difference to the present reality https://www.google.com/imgres?imgurl=...
), but, much more problematic, to all other genres for half a decade or something. Thanks for that, J.R.R!

Being attacked by the bigoted academic society of the time
That´s just ridiculous, Tolkien had to hide and vindicate his amazing work, because it wasn´t highbrow enough for his snobbish, elitist, and old, boring, so called quality literature prone, colleagues and a bigoted, conservative society that wasn´t ready for something new. Better stay with theater texts as books or whatever can be used for patriotic „our writer“ idiocy. Just bad luck that there aren´t enough good, if any, old writers for each country to fuel feelings of literary supremacy. However, it´s one more of these examples of how parochial even seemingly well educated and sophisticated people can be as soon as it comes to close to their cognitive dissonances and socioeconomic status hierarchy overkill.

Putting in meta, connotations, and social criticism
Tolkien was heavily influenced and inspired by war, and the atrocities humans so much love to do to each other until nasty nukes eliminated the option of more WW action, and put the real life implications everywhere in his work. Not just in the form of the big, bad government cooperating with war industry, propaganda machines, and black magic, but with

Corrupted blood
The banality of evil, the attractiveness of the dark side is, as mentioned in „Black, white, and…“ above, is one of the driving forces of the saga and without Tolkiens´experiences, it might have stayed much more superficial and have never reached that deep level of human soul and psyche vivisection. The same with love, without his lifelong, deep bound to this adored wife, he wasn´t allowed to see until reaching full age, the importance of emotions maybe wouldn´t have unfolded and played such an essential role in the work. Expanding this whole, philosophical, psychological somewhat assumptions to his profession as a philologist and, for the standards of the time, mad professor, would go a bit too far, but let´s just say that his expertise might have helped him create both Silmarillion and Lord of the Rings.

Is it outdated?
Very objective thing, even if not including the sociocultural, immense literary impact, Tolkien invested a bit more than the usual fantasy writer in her/his third or fourth series with a new one each new year. Just take the mentioned Silmarillion, the immense details of the world, all the links to the cultural heritage, and the sheer scale and size, and, on top of that, close to everyone agrees that it´s a timeless, genre founding, ingenious masterpiece that will stand the test of not just time, but eternity. Sure, it´s not as accessible as the average, new, overhyped world bestseller, but that´s the same as with Lem, Lovecraft, etc., authors just were used to write in that wacky, overcomplicated, intricate, and difficult to digest language, because they were no narcissistic, lazy, self aggrandizing, god complexed hedonists. I won´t excuse for that, I´m one myself and have N word privileges.
https://tvtropes.org/pmwiki/pmwiki.ph...

Comparing and contrasting fantasy with other genres
Horror, Sci-Fi, or crime had no similar big bangs (fringe theory, by the way, to provoke and insult even more additional people than with just the human degeneration gag above lol) but different founders, prodigies, and subgenres, while fantasy was, stayed, and will be very genre compliant, not to say a bit inflexible in contrast to other genres with much vaster differences, especially sci fi, my bread and peanut butter. So one could say that close to every, no matter if grimdark, YA, high, epic, romantic, etc. fantasy, is always quite the same with some variations of magic systems, the balance of focus on protagonists or antagonists, tone, and the rare establishment of the one or other sub sub genre.

The endless evolution
Close to all human mythology, faith, myths, etc. is fantasy and I see one of its biggest potentials in a fusion to science fantasy, because it opens up all options including any horror or psychothriller crime plot. Without Tolkien, this amazing development couldn´t have taken place so soon and it would have probably needed much longer to establish the (I´m a sci-fi head, sorry) second best genre to subjugate and enslave them all.',
        5, 7),
       (32, 'Will',
        'One of the great works of 20th century literature. I first tried this in high school, but was not able to get through on the first try. The second try, in my early 20s was the charm. Frodo goes on a quest that take in issues of morality, friendship, one''s responsibility towards others, facing one''s fears, courage, danger. While depicting a global battle between good and evil, Tolkien puts a human (or hobbit-ish) face on that conflict. His themes are universal and his characters are very accessible. Frodo, Sam and Gandalf are heroes for the ages, and Gollum is what can happen when normal is corrupted by darkness. This is my favorite series, and taken together with the succeeding pair my favorite book of all time. I have read it at least five times, including aloud to my children. I hope to read it five more.',
        5, 7),
       (33, 'Tharindu', '"Sing hey! for the bath at close of day
that washes the weary mud away!
A loon is he that will not sing:
O! Water Hot is a noble thing!"

"You shouldn''t listen to all you hear,"

When I first watched LOTR TFOTR, a movie that is around 3 hours long, I thought the movie to be insanely long. But now that I''ve finally gotten around to reading the book, I''m shocked that the movie did manage to fit at least half of the contents of the book in to that three hour run, for this is one long and eventful story with so much more information.

"advice is a dangerous gift, even from the wise to the wise,"

Tolkien was never one to have any kind of limitations when it came to vivid imagination. This is something that he shared with The Hobbit, and it seemed to me that the style had indeed improved further. I liked the plot of this one more that The Hobbit, though the surroundings in hobbits were much cozy in my opinion.

"I hope Butterbur sends this promptly. A worthy man, but his memory is like a lumber-room: things wanted always buried."

But as for the plot, it can hardly get any better than this: one of the most well narrated stories I''ve ever read. Despite the book''s length, it is hard to find a place to take a pause. This is that eventful and such a thrill to read. And the ending: I think the chapters themselves wrapped up better compared to that ending which came out of nowhere. It''s almost as if the author wanted you to pick the next one right away. I feel sorry for the people who read the book when it was published in 1954 and had to wait a few months to read the next.

"All that is gold does not glitter,
Not all those who wander are lost;
The old that is strong does not wither,
Deep roots are not reached by the frost.
From the ashes a fire shall be woken,
A light from the shadows shall spring;
Renewed shall be the blade that was broken,
The crownless again shall be king."', 4, 7),
       (34, 'J N', 'Great book so far, what can I say? It''s a touchstone of science fiction. Ordered the first three books separately (because it was actually slightly cheaper to do that than get the box set) and kind of surprised the first book is larger in width and height than the other two. It''s not a big deal I guess but kind of defeats the purpose of getting three books from a series that match each other. I just thought this was odd enough to mention in a review.

UPDATE: Figured it out, the first book is actually in Trade Paperback format; the other two are the NEW Mass Market Paperbacks. Unfortunately on Amazon, the latter is being sold without delineation as the "Paperback" option, often with the "Mass Market" option seeming to be OLDER editions of the MMP size. This is also true for the 6 book (unboxed) set that I later purchased twice, trying to get the rest of the books in the bigger size.

Note: I prefer the bigger size because it stays open when laid on a flat surface or in your lap. That sold it for me. On top of that, the font looks better somehow, and the narrow size just looked and felt kind of weird because the font size was the same but the pages are so narrow (I had an older edition of the first book that was MMP size but smaller font so it didn''t look and read weird).

In all my frustrated searches on Amazon, it seemed really unusually difficult to find the rest of the books in the larger Trade Paperback size, so for your sake, here''s what I figured out after way too much time: A) The official BOXED set is the Trade Paperback size of the first book. B) Or, if you are like me and only wanted to get the first few books in Trade Paperback size, go to the Penguin Random House website, find the individual Dune books, select "Paperback", and click on their Amazon link to buy it here (they also link to other major book dealers, if you prefer). I managed to find books two, three, and four this way., which were the only ones I really wanted anyway.

Note that as of writing this, the second and third books in Trade Paperback size on Amazon are both $9.99 each (same price as the NEW MMP books) but God Emperor is almost full price at $16.99 for some reason.',
        5, 14),
       (35, 'Steven Evans', 'I just got the Dune Hardcover Deluxe edition in the mail today, and this may be the most beautiful hardcover I own. The dust jacket has a soft matte feel to it. The inside front and back covers both have unique and amazing paintings. The pages are blue tipped. I had originally purchased this book to read, but I''m having second thoughts and may just re-read my dog-eared paperback that I bought 20 years ago. I paid $28 for this deluxe hardcover edition and I believe it is well worth it.

Of course, the story itself is 5 stars all the way.', 5, 14),
       (36, 'Joseph', 'Reviewed in the United States on October 4, 2019
Verified Purchase
So much to love and a different take on space based science fiction. I wanted to explore this world, the characters, the universe. All of it is still exciting and fresh feeling even 50 years later. Dune stands alone as it’s own.

Too bad I hate this book.

It is written by a man who may have never actually had a conversation with another living being in his life. He takes the world he created, the characters he created, and renders them lifeless. I don’t care about anyone in the book because why would I? I’ve been given nothing other than basic outlines of them. Plus the second half of this book feels like it was written in a day. The best way I can describe reading this is as if I was reading it through a vaseline covered lens. I can make out something but mostly it’s just vague shapes and colors. It reads like the Bible. Hate this book. Hate it. The only reason I give it two stars is because I actually like the ideas. Too bad someone, anyone else, didn’t write it.',
        2, 14),
       (37, 'Never Gonna Give You Up', 'We''re no strangers to love
You know the rules and so do I
A full commitment''s what I''m thinking of
You wouldn''t get this from any other guy
I just wanna tell you how I''m feeling
Gotta make you understand
Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye
Never gonna tell a lie and hurt you
We''ve known each other for so long
Your heart''s been aching but you''re too shy to say it
Inside we both know what''s been going on
We know the game and we''re gonna play it
And if you ask me how I''m feeling
Don''t tell me you''re too blind to see
Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye
Never gonna tell a lie and hurt you
Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye
Never gonna tell a lie and hurt you
Never gonna give, never gonna give
(Give you up)
We''ve known each other for so long
Your heart''s been aching but you''re too shy to say it
Inside we both know what''s been going on
We know the game and we''re gonna play it
I just wanna tell you how I''m feeling
Gotta make you understand
Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye
Never gonna tell a lie and hurt you
Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye
Never gonna tell a lie and hurt you
Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye', 5, 12),
       (38, 'Nick', 'Hate Harry', 1, 13),
       (39, 'sherm2005',
        'Found that the subject matter did not coincide with my expectations. The more I read, the less I wanted to read. In short, it was simply not interesting enough to retain my attention. Stopped reading less than halfway through. I''m certain there are those who find this book fascinating and very insightful, but the content was often too cerebral for my taste - no surprise.',
        2, 13),
       (40, 'E Griffin',
        'I’d heard a lot about this book. It was certainly a best seller for its time. Also it’s a subject I’m interested in. But.....I’m not particularly bright but I don’t think I’m too slow on the uptake (I’ve two degrees for what they’re worth).....but this book lost me completely. Hawking is a genius, no doubt. But he’s on a different planet to me. A different galaxy or universe even. It’s described as being accessible to “a layman”....whatever that is.....an astrophysicist professor? I admit he lost me completely. I read it all but know it swept over my head in a cloud of cosmic dust in the eons of time going backwards and forwards in time through wormholes encapsulated by gravity and disappearing into black holes at (just below) the speed of light. I gave it an extra star for the occasional bit of humour, obviously included as a nod towards thickos like me. I’m glad I read it though. I know where I stand in the Universe and The Big Bang. Pretty near the bottom of the class.',
        2, 13),
       (41, 'Jeniffer', 'I`m not smarty. Too difficult for me', 2, 11),
       (42, 'Tris', 'I`m blonde.', 3, 11),
       (43, 'Mark',
        'Although condensed and reduced as much as possible, many of the concepts and conclusions defy lay comprehension. Nice try, though, interspersed with Hawking''s dry humor.',
        2, 11),
       (44, 'Little hater', 'Why it hit the New York Times Bestseller list is a mysterious physics questions in itself. While New York Times stated it was entertaining and other critics said "... well written.." It would seem the New York Times is easily entertained.

It read more like a textbook, and in fact, have read several textbooks on similar subjects, that were far more interesting and enjoyable.
The book seems to mostly toot the Hawking "horn" by the one and only, Stephen Hawking.

While his theories are just that, "Theories" this is not really "incredible" work. Try "Cosmos" if your really interested in educational entertainment on this subject.',
        2, 11),
       (45, 'Michael',
        'I did not really enjoy this much at all. Most of it was boring and the story was pretty strange. Aside from the idea presented about murder being acceptable for great men and a path to being special, I was not enthused. I had lots of questions at the end.',
        2, 6),
       (46, 'Robert',
        'I opened the book and saw that the print was incredibly small... somewhere near a 3 pt font. Very difficult for me to read.',
        3, 6),
       (47, 'Lyle',
        'Very strange cadence in the performers voice. Was reading the story, but the method is very distracting. I would not recommend this to my friends.',
        3, 6),
       (48, 'Redand',
        'If my cheap printer could make a hardcover book, I guess this is what I would get. I expected an old (real) hardcover, not a new version that some clown with a cheap book binder made by grabbing the free version of this non-copyrighted material off the web and proclaimed himself to be a book company. Way to go, Simon and Brown... enjoy my twenty bucks I guess.',
        3, 6),
       (49, 'Cole',
        'The crime happens in the first 30 pages and your punishment is reading the rest of the book. I was compelled to read this thing in high school and just didn''t understand it. What high school student would? It''s really, really stupid to try to force this sort of stuff on high schoolers. I read it again (voluntarily) in college to see if I could understand it then. I guess I did but didn''t really feel like I got much out of it. So, at 40+ years of age, I read it again. I''m not an idiot. There are plenty of works of classic literature that I have greatly enjoyed. This book... I guess that whatever is in there just doesn''t register with me. I just find the book boring and annoying and not particularly insightful.',
        3, 6),
       (50, 'Glenn',
        'If you''re looking to buy "Crime and Punishment", make sure you get some samples first and compare them side by side. The difference between this and the Coulson translation is night and day, and you need only compare the first couple paragraphs to see it. If I''d read this version of Crime and Punishment I would not have thought so highly of it as I do. If you don''t have time to compare, do yourself a favor and get the Coulson translation.',
        2, 6),
       (51, 'Berkan',
        'First of all, this is not a review of Dostoyevsky''s masterpiece about the human condition, and better critics than I have already done that. No, this is a review of the product and a cheap and nasty product it is too. I thought that I was buying a new edition with a nice readable typeface, a well-designed dust-wrapper and a new translation. There''s nothing wrong with the Constance Garnett translation except that it is, shall we say, somewhat old-fashioned to our modern ears. The book itself is rubbish, the binding is poor and the paper is of poor quality. The stark contrast between the white paper and the black ink is not pleasing. The printing is smudgy and the typeface is far too small.If you''e thinking of buying this edition then don''t.',
        3, 6),
       (52, 'Dont follow the link',
        'link => https://www.youtube.com/watch?v=dQw4w9WgXcQ <=',
        5, 12);