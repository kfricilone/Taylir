package me.kfricilone.taylir.common.mlir.graph;

import lombok.Value;
import me.kfricilone.taylir.common.mlir.Stmt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Feb 21, 2019.
 */
@Value
public class StmtBlock
{

	private final int id;
	private final List<Stmt> statements = new ArrayList<>();

}
