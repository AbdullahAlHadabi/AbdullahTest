public class GameCharacter {
    public static void main(String[] args) {

        Mage mage = new Mage("Wizard", 100, 50);
        Warrior warrior = new Warrior("Knight", 120, 40);

        mage.attack(warrior);
        warrior.attack(mage);

        System.out.println("Mage Health: " + mage.getHealth());
        System.out.println("Warrior Health: " + warrior.getHealth());
    }
}


    class Character {
        protected String name; // protected so child can use it
        private int health;

        public Character(String name, int health) {
            this.name = name;
            this.health = health;
        }

        public void takeDamage(int damage) {
            health -= damage;
            System.out.println(name + " takes damage: " + damage);
        }

        public int getHealth() {
            return health;
        }

        public void attack(Character target) {
            System.out.println("Character attacks ");
        }
    }

    class Mage extends Character {
        private int mana;

        public Mage(String name, int health, int mana) {
            super(name, health);
            this.mana = mana;
        }

        @Override
        public void attack(Character target) {
            int damage = 25;
            mana -= 34;
            System.out.println(name + " casts a spell ");
            target.takeDamage(damage);
        }

    }

    class Warrior extends Character {
        private int stamina;

        public Warrior(String name, int health, int stamina) {
            super(name, health);
            this.stamina = stamina;
        }

        @Override
        public void attack(Character target) {
            int damage = 20;  // Warrior attack power
            stamina -= 5; // Every attack reduces stamina
            System.out.println(name + " swings sword ");
            target.takeDamage(damage);
        }
    }




