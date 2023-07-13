package model;

/**
 * Enumerated type for the hero types.
 *
 * @author Nathan Hinthorne
 */
public enum HeroTypes {

    SWORDSMAN, // mid hp.
    // basic: sword swing
    // special: sword slice - will almost certainly hit, but also a small chance crit hit. max cooldown 2
    // passive: "parry"  - chance to block monster's attack and deal damage back to it || "resilient defense " - chance to take half damage

    ARCHER, // low hp.
    // basic: shoot - shoot an arrow at the monster, does more damage than other heroes basics, has little higher chance to miss
    // special: triple shot - shoot 3 arrows at the monster. chance for each of them to hit individually
    // incorporate range?

    JUGGERNAUT, // high hp.
    // basic: punch - does much damage than other heroes' basics
    // special: charge - charge at the monster and pound it with fists. 50% chance to deal massive damage, 50% chance to miss
    // passive: "rage" - every time you take damage, you deal more damage on your next turn

    THIEF, // low hp.
    // basic: stab (with obtained weapon)
    // special: steal - steal a potion/xp/weapon(increases dmg) from the monster
    //         (monster can't attack on player's "wasted" turn because they are confused). max cooldown 4, initial cooldown 2

    // passive:
    DOCTOR, // mid hp.
    // basic: syringe - stab monster with syringe (mid damage)
    // special: Anesthetic Mist - spray monster with anesthetic (stuns and poisons monster)
    // passive: every hit you take has a chance to heal you for a small amount (still take damage)

    NINJA, // mid hp.
    // passive: 2 turns at start of battle
    // basic: shuriken or numchucks.
    // special: vanish - run away from battle (leaving monster at same hp you left it at)

    MAGE, // low hp.
    // basic: Ethereal Beam  - blast monster with a condensed beam of light (high damage)
    // special: arcane thunderbolt/arcane surge - strike monster with lightning (extremely high damage)
    // passive - every 3 steps makes you take, you either
    // 1. charge up power of special attack or 2. decrease cooldown of special attack (and special starts on cooldown)

    SCIENTIST, // mid hp.
    // passive: every 10 steps makes you craft a certain kind of damage-dealing potion (going into the inventory)
    //      potion has chance to deal damage or stun monster
    // basic:
    // special:

    BEASTMASTER, // low hp.
    // basic: whip - whip monster (low damage). when you have a monster, do its attack instead
    // special: tame - tame monster (monster becomes your pet and fights for you, takes damage for you). max cooldown 2, initial cooldown 3
    // passive: every 5 steps you take

//    MICHAEL, //
//    GARRET, // lead pipe - bonk sound
//    ETHAN,  // throws burritos at monsters
//    JADON,  // yoyo
//    NATHAN, // hacks into game and makes monsters glitch
//    THE_ROCK // raises eyebrow at monsters

/*    ideas:
        garret - ogre meets him, "hey, I know you, you're the guy who stole my lead pipe!",
                 *bonk sound is played*, ogre is defeated


*/
}
