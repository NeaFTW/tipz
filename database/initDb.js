con = new Mongo();
var db = connect("localhost:27020/tipz");


/* Account collection */
db.account.insert({
    email : "admin@admin.com",
    password : "123",
    firstname : "admin",
    lastname : "admin"
});
db.account.insert({
    email : "zheng_v@epita.fr",
    password : "123",
    firstname : "Valentin",
    lastname : "Zheng"
});
db.account.insert({
    email : "dechen_g@epita.fr",
    password : "123",
    firstname : "Gregoire",
    lastname : "De Chenerilles"
});

for (var i = 0; i < 10; ++i) {
    db.account.insert({
        email : "root" + i + "@root.com",
        password : "123",
        firstname : "root" + i,
        lastname : "root" + i});
}


/* project collection*/
db.project.createIndex( { "id": 1 }, { unique: true } )

db.project.insert({
    id : 1,
    name : "Project 1",
    creationDate : "12/12/2016",
    description : "<p>Calamitosi quisque inopes ut magis ut calamitosi qui et firmitatis amicitiarum et quisque itaque amicitias quam etiam dicere benevolentiae opulenti perstrinxi) ita benevolentiae inopes etiam putentur virium perstrinxi) esse praesidia ii appetere ex paulo benevolentiae ut breviter fieri neque amicitias amicitiarum etiam quam amicitias maxime et ante esse mulierculae opulenti etiam ut putentur firmitatis praesidia et ante mulierculae mulierculae etiam autem ex minimumque minimum amicitias inhumanius quaerant quam quisque autem mulierculae quam magis breviter ut breviter ex esse ita calamitosi viri itaque mulierculae calamitosi qui quam inhumanius ex opulenti itaque inhumanius quisque beati haberet magis quam paulo etiam inopes aiunt.&nbsp;Morborum quam perferentem in medendi ut remedium paucis omnis labes similia morborum ut validum adminiculum colligati noti acerbitates aliud aegritudine purgaverint corpus dominantur satis excogitatum quoniam sospitale torpescit apud lavacro missos validum morborum ita valeant torpescit visa recipiant corpus visa remedium noti ita satis excogitatum domum colligati ante similia hac.</p>",
    amount : 15,
    author : "Admin Admin",
    contact : "admin@admin.com",
    participeNb : 1,
    accountEmail : "admin@admin.com"
});

db.project.insert({
    id : 2,
    name : "Project 2",
    creationDate : "20/12/2016",
    description : "<p>Calamitosi quisque inopes ut magis ut calamitosi qui et firmitatis amicitiarum et quisque itaque amicitias quam etiam dicere benevolentiae opulenti perstrinxi) ita benevolentiae inopes etiam putentur virium perstrinxi) esse praesidia ii appetere ex paulo benevolentiae ut breviter fieri neque amicitias amicitiarum etiam quam amicitias maxime et ante esse mulierculae opulenti etiam ut putentur firmitatis praesidia et ante mulierculae mulierculae etiam autem ex minimumque minimum amicitias inhumanius quaerant quam quisque autem mulierculae quam magis breviter ut breviter ex esse ita calamitosi viri itaque mulierculae calamitosi qui quam inhumanius ex opulenti itaque inhumanius quisque beati haberet magis quam paulo etiam inopes aiunt.&nbsp;Morborum quam perferentem in medendi ut remedium paucis omnis labes similia morborum ut validum adminiculum colligati noti acerbitates aliud aegritudine purgaverint corpus dominantur satis excogitatum quoniam sospitale torpescit apud lavacro missos validum morborum ita valeant torpescit visa recipiant corpus visa remedium noti ita satis excogitatum domum colligati ante similia hac.</p>",
    amount : 0,
    author : "Admin Admin",
    contact : "admin@admin.com",
    participeNb : 0,
    accountEmail : "admin@admin.com"
});

db.project.insert({
    id : 3,
    name : "Project 3",
    creationDate : "29/12/2016",
    description : "<p>Calamitosi quisque inopes ut magis ut calamitosi qui et firmitatis amicitiarum et quisque itaque amicitias quam etiam dicere benevolentiae opulenti perstrinxi) ita benevolentiae inopes etiam putentur virium perstrinxi) esse praesidia ii appetere ex paulo benevolentiae ut breviter fieri neque amicitias amicitiarum etiam quam amicitias maxime et ante esse mulierculae opulenti etiam ut putentur firmitatis praesidia et ante mulierculae mulierculae etiam autem ex minimumque minimum amicitias inhumanius quaerant quam quisque autem mulierculae quam magis breviter ut breviter ex esse ita calamitosi viri itaque mulierculae calamitosi qui quam inhumanius ex opulenti itaque inhumanius quisque beati haberet magis quam paulo etiam inopes aiunt.&nbsp;Morborum quam perferentem in medendi ut remedium paucis omnis labes similia morborum ut validum adminiculum colligati noti acerbitates aliud aegritudine purgaverint corpus dominantur satis excogitatum quoniam sospitale torpescit apud lavacro missos validum morborum ita valeant torpescit visa recipiant corpus visa remedium noti ita satis excogitatum domum colligati ante similia hac.</p>",
    amount : 0,
    author : "Admin Admin",
    contact : "admin@admin.com",
    participeNb : 0,
    accountEmail : "admin@admin.com"
});

for (var i = 4; i < 12; ++i) {
    db.project.insert({
        id : i,
        name : "Project " + i,
        creationDate : i%28 + "/" + i%12 + "/2017",
        description : "<p>Calamitosi quisque inopes ut magis ut calamitosi qui et firmitatis amicitiarum et quisque itaque amicitias quam etiam dicere benevolentiae opulenti perstrinxi) ita benevolentiae inopes etiam putentur virium perstrinxi) esse praesidia ii appetere ex paulo benevolentiae ut breviter fieri neque amicitias amicitiarum etiam quam amicitias maxime et ante esse mulierculae opulenti etiam ut putentur firmitatis praesidia et ante mulierculae mulierculae etiam autem ex minimumque minimum amicitias inhumanius quaerant quam quisque autem mulierculae quam magis breviter ut breviter ex esse ita calamitosi viri itaque mulierculae calamitosi qui quam inhumanius ex opulenti itaque inhumanius quisque beati haberet magis quam paulo etiam inopes aiunt.&nbsp;Morborum quam perferentem in medendi ut remedium paucis omnis labes similia morborum ut validum adminiculum colligati noti acerbitates aliud aegritudine purgaverint corpus dominantur satis excogitatum quoniam sospitale torpescit apud lavacro missos validum morborum ita valeant torpescit visa recipiant corpus visa remedium noti ita satis excogitatum domum colligati ante similia hac.</p>",
        amount : 0,
        author : "Admin Admin",
        contact : "admin@admin.com",
        participeNb : 0,
        accountEmail : "admin@admin.com"
    });
}

/* Counterpart collection */
db.counterpart.createIndex( {"id": 1 }, { unique: true });

db.counterpart.insert({
    id : 1,
    name : "counterpart 1",
    value : 15,
    description : "counterpart 1 description",
    projectId : 1
});

db.counterpart.insert({
    id : 2,
    name : "counterpart 2",
    value : 5,
    description : "counterpart 2 description",
    projectId : 2
});

db.counterpart.insert({
    id : 3,
    name : "counterpart 3",
    value : 30,
    description : "counterpart 3 description",
    projectId : 3
});

for (var i = 4; i < 12; ++i) {
    db.counterpart.insert({
        id : i,
        name : "counterpart " + i,
        value : 50 + i,
        description : "counterpart " + i + " description",
        projectId : i
    });
}

/* account and counterpart join collection*/
db.accountCounterpart.insert({
    accountEmail : "admin@admin.com",
    counterpartId : 1
});