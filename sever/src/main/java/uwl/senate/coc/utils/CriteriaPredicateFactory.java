package uwl.senate.coc.utils;

import java.util.function.Function;
import java.util.function.Predicate;
import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.Criteria;
import uwl.senate.coc.entities.User;

public class CriteriaPredicateFactory {
	private static void validate( ExpressionParser parser, String... expected ) {
		for( int i = 0; i < expected.length; i++ ) {
			if( !parser.peek( i ).equals( expected[i] ) ) throw new IllegalArgumentException(); 
		}
	}
	
	private static void consume(ExpressionParser parser, String... expected  ) {
		for( int i = 0; i < expected.length; i++ ) {
			if( !parser.peek( i ).equals( expected[i] ) ) throw new IllegalArgumentException(); 
		}

		parser.advance( expected.length );
	}

	// always of the form (...)
	public static Predicate<Committee> expression( ExpressionParser parser ) {
		validate( parser, "(" );
		String type = parser.peek( 1 );

		Predicate<Committee> result = null;
		switch( type ) {
			case "all" : result = all( parser ); break;
			case "size" : result = sizeOf( parser ); break;
			case "college" : result = college( parser ); break;
			case "department" : result = department( parser );break;
			case "gender" : result = gender( parser );break;
			case "soe" : result = soe( parser); break;
			case "chair": result = chair(parser); break;
			case "admin" : result = adm( parser); break;
			case "tenured": result = tenured(parser); break;
			case "rank":  result = rank(parser); break;
			default: throw new IllegalArgumentException();
		}

		return result;
	}

	// (all tenured)
	// (all full-time)
	// (all grad-status)
	public static Predicate<Committee> all( ExpressionParser parser ) {
		consume( parser, "(", "all" );

		String property = parser.nextToken();
		Function<User, Boolean> getter;
		switch( property ) {
			case "tenured" : getter = User::getTenured; break;
			case "admin" : getter = User::getAdminResponsibility; break;
			case "soe" : getter = User::getSoe; break;
			case "grad-status" : getter = User::getGradStatus; break;
			default: throw new IllegalArgumentException();
		}

		consume( parser, ")" );
		return (committee) -> committee.getMembers().stream().allMatch( u -> getter.apply(u) );
	}

	// (rank Full Professor 9)
	public static Predicate<Committee> rank( ExpressionParser parser ) {
		consume( parser, "(", "rank" );
		String rank = parser.nextToken() + ' ' + parser.nextToken();
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = u -> u.getCollege() != null && u.getCollege().getName().toLowerCase().equals( rank );
		consume( parser, ")");

		return committee ->
				committee
						.getMembers()
						.stream()
						.filter( userCheck )
						.count() >= count;
	}

	// (college cls 3)
	// (college csh 3) 
	// (college cba 1)
	public static Predicate<Committee> college( ExpressionParser parser ) {
		consume( parser, "(", "college" );
		String college = parser.nextToken();
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = u -> u.getCollege() != null && u.getCollege().getName().toLowerCase().equals( college );
		consume( parser, ")");

		return committee -> 
		committee
			.getMembers()
				.stream()
				.filter( userCheck )
				.count() >= count;
	}


	// (department CS 3)
	// (department BIO 3)
	// (department EDS 1)
	public static Predicate<Committee> department( ExpressionParser parser ) {
		consume( parser, "(", "department" );
		String dept = parser.nextToken();
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = u -> u.getDept() != null && u.getDept().getName().toLowerCase().equals( dept );
		consume( parser, ")");

		return committee ->
				committee
						.getMembers()
						.stream()
						.filter( userCheck )
						.count() >= count;
	}

	// (soe 1)
	public static Predicate<Committee> soe( ExpressionParser parser ) {
		consume( parser, "(", "soe" );
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = User::getSoe;
		consume( parser, ")");

		return committee -> 
		committee
			.getMembers()
			.stream()
				.filter( userCheck )
				.count() >= count;
	}

	// (ten 1)
	public static Predicate<Committee> tenured( ExpressionParser parser ) {
		consume( parser, "(", "tenured" );
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = User::getTenured;
		consume( parser, ")");

		return committee ->
				committee
						.getMembers()
						.stream()
						.filter( userCheck )
						.count() >= count;
	}

	// (admim 1)
	public static Predicate<Committee> adm( ExpressionParser parser ) {
		consume( parser, "(", "admin" );
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = User::getAdminResponsibility;
		consume( parser, ")");

		return committee ->
				committee
						.getMembers()
						.stream()
						.filter( userCheck )
						.count() >= count;
	}

	// (chair 1)
	public static Predicate<Committee> chair( ExpressionParser parser ) {
		consume( parser, "(", "chair" );
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = User::getChair;
		consume( parser, ")");

		return committee ->
				committee
						.getMembers()
						.stream()
						.filter( userCheck )
						.count() >= count;
	}

	// (size 9)
	public static Predicate<Committee> sizeOf( ExpressionParser parser ) {
		consume( parser, "(", "size" );
		Integer size = Integer.parseInt( parser.nextToken() );
		consume( parser, ")" );
		
		return c -> c.getMembers().size() == size;
	}


	private static Predicate<Committee> gender(ExpressionParser parser) {
		consume( parser, "(", "gender" );
		String gender = parser.nextToken();
		Integer count = Integer.parseInt( parser.nextToken() );
		Predicate<User> userCheck = u -> u.getGender() != null && u.getGender().getName().toLowerCase().equals( gender );
		consume( parser, ")");

		return committee ->
				committee
						.getMembers()
						.stream()
						.filter( userCheck )
						.count() >= count;
	}

	public static Predicate<Committee> build(Criteria crit) {
		ExpressionParser parser = new ExpressionParser( crit.getCriteria().toLowerCase() );
		return expression( parser );
	}
}
