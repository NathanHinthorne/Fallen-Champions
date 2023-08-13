package model.characters.heroes;

/**
 * Enumerated type for the hero types.
 *
 * @author Nathan Hinthorne
 */
public enum HeroTypes {

    // tips for putting strategy into the game:
    // 1. give ability to block damage by pressing a button before a delay ends??

    // "blinded" - monster has higher chance to miss
    // "stuck", "stun" - monster's turn is skipped
    // "weakened" - monster does 50% damage on next hit
    // "vulnerable" - next hit on monster will inflict 3x the damage to it
    // "silenced" - monster can't use special attack

    SWORDSMAN, // mid hp.
    // basic: sword strike
    // special: mighty swing - will almost certainly hit, but also a small chance crit hit. chance to inflict silence. max cooldown 2
    // passive: battle stance - higher than 50% health, do more damage. lower than 50%, take less damage

    ARCHER, // low hp.
    // basic: Swift Draw - shoot an arrow at the monster, does more damage than other heroes basics, has little higher chance to miss, chance to inflict weaken
    // special: Triple Shot - shoot 3 arrows at the monster. chance for each of them to hit individually
    // passive: ranged attack (with 2 uses between battles) hitting revealed monsters before engaging in battle

    JUGGERNAUT, // high hp.
    // basic: punch - does much damage than other heroes' basics
    // special: split ground - punch the ground. inflict stuck. max cooldown 2, initial cooldown 1
    // passive: fury - every time you take damage, you deal more damage

    THIEF, // low hp.
    // basic: stab - (with obtained weapon) chance to inflict vulnerable
    // special: steal - steal a potion/xp/weapon(increases dmg) from the monster, always inflict stuck
    //         (monster can't attack on player's "wasted" turn) max cooldown 4, initial cooldown 2
    // passive "quick reflexes" - chance to counterattack

    DOCTOR, // mid hp.
    // basic: syringe - stab monster with syringe (mid damage), chance to inflict weaken or vulnerable
    // special: Anesthetic Mist - spray monster with anesthetic (stuns and poisons monster)
    // passive: every hit you take has a chance to heal you for a small amount (still take damage)

    NINJA, // mid hp.
    // passive: 2 turns at start of battle, throw shuriken with ranged attack
    // basic: numchucks - hit monster with numchucks (medium damage), chance to inflict silenced
    // special: blowgun - shoot monster with blowgun (low damage), inflict poisoned

    MAGE, // low hp.
    // basic: Ethereal Beam  - blast monster with a condensed beam of light (high damage), chance to inflict silence
    // special: arcane thunderbolt/arcane surge - strike monster with lightning (extremely high damage)
    // passive: every 3 steps makes you take, you either
    // 1. charge up power of special attack or 2. decrease cooldown of special attack (and special starts on cooldown)

    SCIENTIST, // mid hp.
    // passive: every 8 steps makes you craft a certain kind of damage-dealing potion (going into the inventory)
    //      potion has chance to deal damage, poison, or stuckify monster. has 6 inventory slots
    // basic: Electrocute - shoot monster with electricity (low damage, stuck chance)
    // special: ???

    BEASTMASTER, // mid hp.
    // basic: whip - whip monster (low damage), chance to inflict stuck. when you have a monster, do its attack instead
    // special: tame - tame monster (monster becomes your pet and fights for you, takes damage for you). max cooldown 2, initial cooldown 3
    // passive: every 5 steps you take ...?



//    MICHAEL, //
//    GARRET, // lead pipe - bonk sound
//    ETHAN,  // throws burritos at monsters (chance to instantly defeat or heal slightly)
//    JADON,  // baseball bat "hits home run into monster's face" & "throws a heater at monster's stomach", "throws yoyo around monster's legs, tripping it"
//    NATHAN, // hacks into game and makes monsters glitch
//    AARON   // basic: chucks laptop at monsters. special: flinging mouse (with cord), passive: sees 1 square ahead (in a circle)
//    THE_ROCK // raises eyebrow at monsters, throws goblins at other monsters


/*    ideas:
        garret - ogre meets him, "hey, I know you, you're the guy who stole my lead pipe!",
                 *bonk sound is played*, ogre is defeated


*/
}
